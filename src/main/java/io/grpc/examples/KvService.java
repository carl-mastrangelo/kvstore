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
import java.util.HashMap;
import java.util.Map;


/**
 * This is the actual server logic.  It includes a basic key value store, as well as implements
 * thread safe methods for creating, retrieving, updating, and deleting values.  (These are
 * commonly known as "CRUD" operations.)
 */
final class KvService extends KeyValueServiceImplBase {

  private final Map<ByteBuffer, ByteBuffer> store = new HashMap<>();

  @Override
  public synchronized void create(
      CreateRequest request, StreamObserver<CreateResponse> responseObserver) {
    ByteBuffer key = request.getKey().asReadOnlyByteBuffer();
    ByteBuffer value = request.getValue().asReadOnlyByteBuffer();
    if (store.putIfAbsent(key, value) == null) {
      responseObserver.onNext(CreateResponse.getDefaultInstance());
      responseObserver.onCompleted();
      return;
    }
    responseObserver.onError(Status.ALREADY_EXISTS.asRuntimeException());
  }

  @Override
  public synchronized void retrieve(RetrieveRequest request,
      StreamObserver<RetrieveResponse> responseObserver) {
    ByteBuffer key = request.getKey().asReadOnlyByteBuffer();
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
  public synchronized void update(
      UpdateRequest request, StreamObserver<UpdateResponse> responseObserver) {
    ByteBuffer key = request.getKey().asReadOnlyByteBuffer();
    ByteBuffer newValue = request.getValue().asReadOnlyByteBuffer();
    ByteBuffer oldValue = store.get(key);
    if (oldValue == null) {
      responseObserver.onError(Status.NOT_FOUND.asRuntimeException());
      return;
    }
    store.replace(key, oldValue, newValue);
    responseObserver.onNext(UpdateResponse.getDefaultInstance());
    responseObserver.onCompleted();
  }

  @Override
  public synchronized void delete(
      DeleteRequest request, StreamObserver<DeleteResponse> responseObserver) {
    ByteBuffer key = request.getKey().asReadOnlyByteBuffer();
    store.remove(key);
    responseObserver.onNext(DeleteResponse.getDefaultInstance());
    responseObserver.onCompleted();
  }
}
