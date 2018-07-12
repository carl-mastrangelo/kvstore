package io.grpc.examples;

import com.google.gson.Gson;
import io.grpc.BindableService;
import io.grpc.MethodDescriptor;
import io.grpc.MethodDescriptor.Marshaller;
import io.grpc.MethodDescriptor.MethodType;
import io.grpc.ServerServiceDefinition;
import io.grpc.stub.ServerCalls;
import io.grpc.stub.StreamObserver;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;

final class KvGson {

  private static final String SERVICE_NAME = "io.grpc.KeyValueService";

  static final class CreateRequest {
    byte[] key;
    byte[] value;
  }

  static final class CreateResponse{}

  static final MethodDescriptor<CreateRequest, CreateResponse> CREATE_METHOD =
      MethodDescriptor.newBuilder(
          marshallerFor(CreateRequest.class),
          marshallerFor(CreateResponse.class))
          .setFullMethodName(
              MethodDescriptor.generateFullMethodName(SERVICE_NAME, "Create"))
          .setType(MethodType.UNARY)
          .setSampledToLocalTracing(true)
          .build();

  static final class RetrieveRequest {
    byte[] key;
  }

  static final class RetrieveResponse {
    byte[] value;
  }

  static final MethodDescriptor<RetrieveRequest, RetrieveResponse> RETRIEVE_METHOD =
      MethodDescriptor.newBuilder(
          marshallerFor(RetrieveRequest.class),
          marshallerFor(RetrieveResponse.class))
          .setFullMethodName(
              MethodDescriptor.generateFullMethodName(SERVICE_NAME, "Retrieve"))
          .setType(MethodType.UNARY)
          .setSampledToLocalTracing(true)
          .build();

  static final class UpdateRequest {
    byte[] key;
    byte[] value;
  }

  static final class UpdateResponse {}

  static final MethodDescriptor<UpdateRequest, UpdateResponse> UPDATE_METHOD =
      MethodDescriptor.newBuilder(
          marshallerFor(UpdateRequest.class),
          marshallerFor(UpdateResponse.class))
          .setFullMethodName(
              MethodDescriptor.generateFullMethodName(SERVICE_NAME, "Update"))
          .setType(MethodType.UNARY)
          .setSampledToLocalTracing(true)
          .build();


  static final class DeleteRequest {
    byte[] key;
  }

  static final class DeleteResponse {}

  static final MethodDescriptor<DeleteRequest, DeleteResponse> DELETE_METHOD =
      MethodDescriptor.newBuilder(
          marshallerFor(DeleteRequest.class),
          marshallerFor(DeleteResponse.class))
          .setFullMethodName(
              MethodDescriptor.generateFullMethodName(SERVICE_NAME, "Delete"))
          .setType(MethodType.UNARY)
          .setSampledToLocalTracing(true)
          .build();

  static abstract class KeyValueServiceImplBase implements BindableService {
    public abstract void create(
        KvGson.CreateRequest request, StreamObserver<CreateResponse> responseObserver);

    public abstract void retrieve(KvGson.RetrieveRequest request,
        StreamObserver<KvGson.RetrieveResponse> responseObserver);

    public abstract void update(
        KvGson.UpdateRequest request, StreamObserver<KvGson.UpdateResponse> responseObserver);

    public abstract void delete(
        KvGson.DeleteRequest request, StreamObserver<KvGson.DeleteResponse> responseObserver);

    @Override
    public final ServerServiceDefinition bindService() {
      ServerServiceDefinition.Builder ssd = ServerServiceDefinition.builder(SERVICE_NAME);
      ssd.addMethod(CREATE_METHOD, ServerCalls.asyncUnaryCall(
          (request, responseObserver) -> create(request, responseObserver)));
      ssd.addMethod(RETRIEVE_METHOD, ServerCalls.asyncUnaryCall(
          (request, responseObserver) -> retrieve(request, responseObserver)));
      ssd.addMethod(UPDATE_METHOD, ServerCalls.asyncUnaryCall(
          (request, responseObserver) -> update(request, responseObserver)));
      ssd.addMethod(DELETE_METHOD, ServerCalls.asyncUnaryCall(
          (request, responseObserver) -> delete(request, responseObserver)));
      return ssd.build();
    }
  }

  static <T> Marshaller<T> marshallerFor(Class<T> clz) {
    Gson gson = new Gson();
    return new Marshaller<T>() {
      @Override
      public InputStream stream(T value) {
        return new ByteArrayInputStream(gson.toJson(value, clz).getBytes(StandardCharsets.UTF_8));
      }

      @Override
      public T parse(InputStream stream) {
        return gson.fromJson(new InputStreamReader(stream, StandardCharsets.UTF_8), clz);
      }
    };
  }
}
