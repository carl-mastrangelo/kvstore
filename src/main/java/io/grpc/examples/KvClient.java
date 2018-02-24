package io.grpc.examples;

import com.google.protobuf.ByteString;
import io.grpc.Channel;
import io.grpc.Status.Code;
import io.grpc.StatusRuntimeException;
import io.grpc.examples.proto.CreateRequest;
import io.grpc.examples.proto.CreateResponse;
import io.grpc.examples.proto.DeleteRequest;
import io.grpc.examples.proto.DeleteResponse;
import io.grpc.examples.proto.KeyValueServiceGrpc;
import io.grpc.examples.proto.KeyValueServiceGrpc.KeyValueServiceBlockingStub;
import io.grpc.examples.proto.RetrieveRequest;
import io.grpc.examples.proto.RetrieveResponse;
import io.grpc.examples.proto.UpdateRequest;
import io.grpc.examples.proto.UpdateResponse;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
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

  private long rpcCount;

  KvClient(Channel channel) {
    this.channel = channel;
  }

  long getRpcCount() {
    return rpcCount;
  }

  /**
   * Does the client work until {@code done.get()} returns true.  Callers should set done to true,
   * and wait for this method to return.
   */
  void doClientWork(AtomicBoolean done) {
    Random random = new Random();
    KeyValueServiceBlockingStub stub = KeyValueServiceGrpc.newBlockingStub(channel);

    while (!done.get()) {
      // Pick a random CRUD action to take.
      int command = random.nextInt(4);
      if (command == 0) {
        doCreate(stub);
        continue;
      }
      // If we don't know about any keys, retry with a new random action.
      if (knownKeys.isEmpty()) {
        continue;
      }
      if (command == 1) {
        doRetrieve(stub);
      } else if (command == 2) {
        doUpdate(stub);
      } else if (command == 3) {
        doDelete(stub);
      } else {
        throw new AssertionError();
      }
      rpcCount++;
    }
  }

  /**
   * Creates a random key and value.
   */
  private void doCreate(KeyValueServiceBlockingStub stub) {
    ByteString key = createRandomKey();
    try {
      CreateResponse res = stub.create(
          CreateRequest.newBuilder()
              .setKey(key)
              .setValue(randomBytes(MEAN_VALUE_SIZE))
              .build());
      if (!res.equals(CreateResponse.getDefaultInstance())) {
        throw new RuntimeException("Invalid response");
      }
    } catch (StatusRuntimeException e) {
      if (e.getStatus().getCode() == Code.ALREADY_EXISTS) {
        knownKeys.remove(key);
        logger.log(Level.INFO, "Key already existed", e);
      } else {
        throw e;
      }
    }
  }

  /**
   * Retrieves the value of a random key.
   */
  private void doRetrieve(KeyValueServiceBlockingStub stub) {
    ByteString key = knownKeys.getRandomKey();
    try {
      RetrieveResponse res = stub.retrieve(RetrieveRequest.newBuilder()
          .setKey(key)
          .build());
      if (res.getValue().size() < 1) {
        throw new RuntimeException("Invalid response");
      }
    } catch (StatusRuntimeException e) {
      if (e.getStatus().getCode() == Code.NOT_FOUND) {
        knownKeys.remove(key);
        logger.log(Level.INFO, "Key not found", e);
      } else {
        throw e;
      }
    }
  }

  /**
   * Updates a random key with a random value.
   */
  private void doUpdate(KeyValueServiceBlockingStub stub) {
    ByteString key = knownKeys.getRandomKey();
    try {
      UpdateResponse res = stub.update(UpdateRequest.newBuilder()
          .setKey(key)
          .setValue(randomBytes(MEAN_VALUE_SIZE))
          .build());
      if (!res.equals(UpdateResponse.getDefaultInstance())) {
        throw new RuntimeException("Invalid response");
      }
    } catch (StatusRuntimeException e) {
      if (e.getStatus().getCode() == Code.NOT_FOUND) {
        knownKeys.remove(key);
        logger.log(Level.INFO, "Key not found", e);
      } else {
        throw e;
      }
    }
  }

  /**
   * Deletes the value of a random key.
   */
  private void doDelete(KeyValueServiceBlockingStub stub) {
    ByteString key = knownKeys.getRandomKey();
    DeleteResponse res = stub.delete(DeleteRequest.newBuilder().setKey(key).build());
    knownKeys.remove(key);
    if (!res.equals(DeleteResponse.getDefaultInstance())) {
      throw new RuntimeException("Invalid response");
    }
  }

  /**
   * Creates and adds a key to the set of known keys.
   */
  private ByteString createRandomKey() {
    ByteString key;
    do {
      key = randomBytes(MEAN_KEY_SIZE);
    } while (!knownKeys.add(key));
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
