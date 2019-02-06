package io.grpc.examples;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;


/**
 * This is the actual server logic.  It includes a basic key value store, as well as implements
 * thread safe methods for creating, retrieving, updating, and deleting values.  (These are
 * commonly known as "CRUD" operations.)
 */
final class KvService extends KvJava.KeyValueServiceImplBase {

  private static final long READ_DELAY_MILLIS = 10;
  private static final long WRITE_DELAY_MILLIS = 50;

  private final ConcurrentMap<ByteBuffer, ByteBuffer> store = new ConcurrentHashMap<>();

  @Override
  public void create(
      KvJava.CreateRequest request, StreamObserver<KvJava.CreateResponse> responseObserver) {
    ByteBuffer key = ByteBuffer.wrap(request.key);
    ByteBuffer value = ByteBuffer.wrap(request.value);
    simulateWork(WRITE_DELAY_MILLIS);
    if (store.putIfAbsent(key, value) == null) {
      responseObserver.onNext(new KvJava.CreateResponse());
      responseObserver.onCompleted();
      return;
    }
    responseObserver.onError(Status.ALREADY_EXISTS.asRuntimeException());
  }

  @Override
  public void retrieve(KvJava.RetrieveRequest request,
      StreamObserver<KvJava.RetrieveResponse> responseObserver) {
    ByteBuffer key = ByteBuffer.wrap(request.key);
    simulateWork(READ_DELAY_MILLIS);
    ByteBuffer value = store.get(key);
    if (value != null) {
      KvJava.RetrieveResponse response = new KvJava.RetrieveResponse();
      response.value = value.array();
      responseObserver.onNext(response);
      responseObserver.onCompleted();
      return;
    }
    responseObserver.onError(Status.NOT_FOUND.asRuntimeException());
  }

  @Override
  public void update(
      KvJava.UpdateRequest request, StreamObserver<KvJava.UpdateResponse> responseObserver) {
    ByteBuffer key = ByteBuffer.wrap(request.key);
    ByteBuffer newValue = ByteBuffer.wrap(request.value);
    simulateWork(WRITE_DELAY_MILLIS);
    ByteBuffer oldValue;
    do {
      oldValue = store.get(key);
      if (oldValue == null) {
        responseObserver.onError(Status.NOT_FOUND.asRuntimeException());
        return;
      }
    } while (!store.replace(key, oldValue, newValue));
    responseObserver.onNext(new KvJava.UpdateResponse());
    responseObserver.onCompleted();
  }

  @Override
  public void delete(
      KvJava.DeleteRequest request, StreamObserver<KvJava.DeleteResponse> responseObserver) {
    ByteBuffer key = ByteBuffer.wrap(request.key);
    simulateWork(WRITE_DELAY_MILLIS);
    store.remove(key);
    responseObserver.onNext(new KvJava.DeleteResponse());
    responseObserver.onCompleted();
  }

  private static void simulateWork(long millis) {
    try {
      TimeUnit.MILLISECONDS.sleep(millis);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException(e);
    }
  }
}
