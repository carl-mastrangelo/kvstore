package io.grpc.examples;

import com.google.protobuf.ByteString;
import io.grpc.Status;
import io.grpc.examples.proto.CreateRequest;
import io.grpc.examples.proto.CreateResponse;
import io.grpc.examples.proto.DeleteRequest;
import io.grpc.examples.proto.DeleteResponse;
import io.grpc.examples.proto.KeyValueServiceGrpc.KeyValueServiceImplBase;
import io.grpc.examples.proto.RetrieveRequest;
import io.grpc.examples.proto.RetrieveResponse;
import io.grpc.examples.proto.UpdateRequest;
import io.grpc.examples.proto.UpdateResponse;
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
final class KvService extends KeyValueServiceImplBase {

  private static final long READ_DELAY_MILLIS = 10;
  private static final long WRITE_DELAY_MILLIS = 50;

  private final ConcurrentMap<ByteBuffer, ByteBuffer> store = new ConcurrentHashMap<>();

  @Override
  public void create(
      CreateRequest request, StreamObserver<CreateResponse> responseObserver) {
    ByteBuffer key = request.getKey().asReadOnlyByteBuffer();
    ByteBuffer value = request.getValue().asReadOnlyByteBuffer();
    simulateWork(WRITE_DELAY_MILLIS);
    if (store.putIfAbsent(key, value) == null) {
      responseObserver.onNext(CreateResponse.getDefaultInstance());
      responseObserver.onCompleted();
      return;
    }
    responseObserver.onError(Status.ALREADY_EXISTS.asRuntimeException());
  }

  @Override
  public void retrieve(RetrieveRequest request,
      StreamObserver<RetrieveResponse> responseObserver) {
    ByteBuffer key = request.getKey().asReadOnlyByteBuffer();
    simulateWork(READ_DELAY_MILLIS);
    ByteBuffer value = store.get(key);
    if (value != null) {
      responseObserver.onNext(
          RetrieveResponse.newBuilder().setValue(ByteString.copyFrom(value.slice())).build());
      responseObserver.onCompleted();
      return;
    }
    responseObserver.onError(Status.NOT_FOUND.asRuntimeException());
  }

  @Override
  public void update(
      UpdateRequest request, StreamObserver<UpdateResponse> responseObserver) {
    ByteBuffer key = request.getKey().asReadOnlyByteBuffer();
    ByteBuffer newValue = request.getValue().asReadOnlyByteBuffer();
    simulateWork(WRITE_DELAY_MILLIS);
    ByteBuffer oldValue;
    do {
      oldValue = store.get(key);
      if (oldValue == null) {
        responseObserver.onError(Status.NOT_FOUND.asRuntimeException());
        return;
      }
    } while (!store.replace(key, oldValue, newValue));
    responseObserver.onNext(UpdateResponse.getDefaultInstance());
    responseObserver.onCompleted();
  }

  @Override
  public void delete(
      DeleteRequest request, StreamObserver<DeleteResponse> responseObserver) {
    ByteBuffer key = request.getKey().asReadOnlyByteBuffer();
    simulateWork(WRITE_DELAY_MILLIS);
    store.remove(key);
    responseObserver.onNext(DeleteResponse.getDefaultInstance());
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
