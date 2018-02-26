package io.grpc.examples;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.protobuf.ByteString;
import io.grpc.Channel;
import io.grpc.Status;
import io.grpc.Status.Code;
import io.grpc.StatusRuntimeException;
import io.grpc.examples.proto.CreateRequest;
import io.grpc.examples.proto.CreateResponse;
import io.grpc.examples.proto.DeleteRequest;
import io.grpc.examples.proto.DeleteResponse;
import io.grpc.examples.proto.KeyValueServiceGrpc;
import io.grpc.examples.proto.KeyValueServiceGrpc.KeyValueServiceFutureStub;
import io.grpc.examples.proto.RetrieveRequest;
import io.grpc.examples.proto.RetrieveResponse;
import io.grpc.examples.proto.UpdateRequest;
import io.grpc.examples.proto.UpdateResponse;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Performs sample work load, by creating random keys and values, retrieving them, updating them,
 * and deleting them.  A real program would actually use the values, and they wouldn't be random.
 */
final class KvClient {
  private static final Logger logger = Logger.getLogger(KvClient.class.getName());

  private final int MEAN_KEY_SIZE = 64;
  private final int MEAN_VALUE_SIZE = 65536;

  private final RandomAccessSet<ByteString> knownKeys = new RandomAccessSet<>();
  private final Channel channel;

  private AtomicLong rpcCount = new AtomicLong();
  private final Semaphore limiter = new Semaphore(100);

  KvClient(Channel channel) {
    this.channel = channel;
  }

  long getRpcCount() {
    return rpcCount.get();
  }

  /**
   * Does the client work until {@code done.get()} returns true.  Callers should set done to true,
   * and wait for this method to return.
   */
  void doClientWork(AtomicBoolean done) throws InterruptedException {
    Random random = new Random();
    KeyValueServiceFutureStub stub = KeyValueServiceGrpc.newFutureStub(channel);
    AtomicReference<Throwable> errors = new AtomicReference<>();

    while (!done.get() && errors.get() == null) {
      // Pick a random CRUD action to take.
      int command = random.nextInt(4);
      if (command == 0) {
        doCreate(stub, errors);
        continue;
      }
      synchronized (knownKeys) {
        // If we don't know about any keys, retry with a new random action.
        if (knownKeys.isEmpty()) {
          continue;
        }
      }
      if (command == 1) {
        doRetrieve(stub, errors);
      } else if (command == 2) {
        doUpdate(stub, errors);
      } else if (command == 3) {
        doDelete(stub, errors);
      } else {
        throw new AssertionError();
      }
    }
    if (errors.get() != null) {
      throw new RuntimeException(errors.get());
    }
  }

  /**
   * Creates a random key and value.
   */
  private void doCreate(KeyValueServiceFutureStub stub, AtomicReference<Throwable> error)
      throws InterruptedException {
    limiter.acquire();
    ByteString key = createRandomKey();
    ListenableFuture<CreateResponse> res = stub.create(
        CreateRequest.newBuilder()
            .setKey(key)
            .setValue(randomBytes(MEAN_VALUE_SIZE))
            .build());
    res.addListener(() ->  {
      rpcCount.incrementAndGet();
      limiter.release();
    }, MoreExecutors.directExecutor());
    Futures.addCallback(res, new FutureCallback<CreateResponse>() {
      @Override
      public void onSuccess(CreateResponse result) {
        if (!result.equals(CreateResponse.getDefaultInstance())) {
          error.compareAndSet(null, new RuntimeException("Invalid response"));
        }
        synchronized (knownKeys) {
          knownKeys.add(key);
        }
      }

      @Override
      public void onFailure(Throwable t) {
        Status status = Status.fromThrowable(t);
        if (status.getCode() == Code.ALREADY_EXISTS) {
          synchronized (knownKeys) {
            knownKeys.remove(key);
          }
          logger.log(Level.INFO, "Key already existed", t);
        } else {
          error.compareAndSet(null, t);
        }
      }
    });
  }

  /**
   * Retrieves the value of a random key.
   */
  private void doRetrieve(KeyValueServiceFutureStub stub, AtomicReference<Throwable> error)
      throws InterruptedException {
    limiter.acquire();
    ByteString key;
    synchronized (knownKeys) {
      key = knownKeys.getRandomKey();
    }
    ListenableFuture<RetrieveResponse> res = stub.retrieve(RetrieveRequest.newBuilder()
        .setKey(key)
        .build());
    res.addListener(() ->  {
      rpcCount.incrementAndGet();
      limiter.release();
    }, MoreExecutors.directExecutor());
    Futures.addCallback(res, new FutureCallback<RetrieveResponse>() {
      @Override
      public void onSuccess(RetrieveResponse result) {
        if (result.getValue().size() < 1) {
          error.compareAndSet(null, new RuntimeException("Invalid response"));
        }
      }

      @Override
      public void onFailure(Throwable t) {
        Status status = Status.fromThrowable(t);
        if (status.getCode() == Code.NOT_FOUND) {
          synchronized (knownKeys) {
            knownKeys.remove(key);
          }
          logger.log(Level.INFO, "Key not found", t);
        } else {
          error.compareAndSet(null, t);
        }
      }
    });
  }

  /**
   * Updates a random key with a random value.
   */
  private void doUpdate(KeyValueServiceFutureStub stub, AtomicReference<Throwable> error)
      throws InterruptedException {
    limiter.acquire();
    ByteString key;
    synchronized (knownKeys) {
      key = knownKeys.getRandomKey();
    }
    ListenableFuture<UpdateResponse> res = stub.update(UpdateRequest.newBuilder()
        .setKey(key)
        .setValue(randomBytes(MEAN_VALUE_SIZE))
        .build());
    res.addListener(() ->  {
      rpcCount.incrementAndGet();
      limiter.release();
    }, MoreExecutors.directExecutor());
    Futures.addCallback(res, new FutureCallback<UpdateResponse>() {
      @Override
      public void onSuccess(UpdateResponse result) {
        if (!result.equals(UpdateResponse.getDefaultInstance())) {
          error.compareAndSet(null, new RuntimeException("Invalid response"));
        }
      }

      @Override
      public void onFailure(Throwable t) {
        Status status = Status.fromThrowable(t);
        if (status.getCode() == Code.NOT_FOUND) {
          synchronized (knownKeys) {
            knownKeys.remove(key);
          }
          logger.log(Level.INFO, "Key not found", t);
        } else {
          error.compareAndSet(null, t);
        }
      }
    });
  }

  /**
   * Deletes the value of a random key.
   */
  private void doDelete(KeyValueServiceFutureStub stub, AtomicReference<Throwable> error)
      throws InterruptedException {
    limiter.acquire();
    ByteString key;
    synchronized (knownKeys) {
      key = knownKeys.getRandomKey();
      knownKeys.remove(key);
    }
    ListenableFuture<DeleteResponse> res = stub.delete(DeleteRequest.newBuilder().setKey(key).build());
    res.addListener(() ->  {
      rpcCount.incrementAndGet();
      limiter.release();
    }, MoreExecutors.directExecutor());
    Futures.addCallback(res, new FutureCallback<DeleteResponse>() {
      @Override
      public void onSuccess(DeleteResponse result) {
        if (!result.equals(DeleteResponse.getDefaultInstance())) {
          error.compareAndSet(null, new RuntimeException("Invalid response"));
        }
      }

      @Override
      public void onFailure(Throwable t) {
        Status status = Status.fromThrowable(t);
        if (status.getCode() == Code.NOT_FOUND) {
          logger.log(Level.INFO, "Key not found", t);
        } else {
          error.compareAndSet(null, t);
        }
      }
    });
  }

  /**
   * Creates and adds a key to the set of known keys.
   */
  private ByteString createRandomKey() {
    ByteString key;
    do {
      key = randomBytes(MEAN_KEY_SIZE);
    } while (knownKeys.contains(key));
    return key;
  }

  /**
   * Creates an exponentially sized byte string with a mean size.
   */
  private static ByteString randomBytes(int mean) {
    Random random = new Random();
    // An exponentially distributed random number.
    int size = (int) Math.round(mean * -Math.log(1 - random.nextDouble()));
    byte[] bytes = new byte[1 + size];
    random.nextBytes(bytes);
    return ByteString.copyFrom(bytes);
  }
}
