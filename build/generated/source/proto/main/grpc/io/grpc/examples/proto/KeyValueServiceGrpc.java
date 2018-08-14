package io.grpc.examples.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.10.0)",
    comments = "Source: kvstore.proto")
public final class KeyValueServiceGrpc {

  private KeyValueServiceGrpc() {}

  public static final String SERVICE_NAME = "io.grpc.KeyValueService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getCreateMethod()} instead. 
  public static final io.grpc.MethodDescriptor<io.grpc.examples.proto.CreateRequest,
      io.grpc.examples.proto.CreateResponse> METHOD_CREATE = getCreateMethodHelper();

  private static volatile io.grpc.MethodDescriptor<io.grpc.examples.proto.CreateRequest,
      io.grpc.examples.proto.CreateResponse> getCreateMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<io.grpc.examples.proto.CreateRequest,
      io.grpc.examples.proto.CreateResponse> getCreateMethod() {
    return getCreateMethodHelper();
  }

  private static io.grpc.MethodDescriptor<io.grpc.examples.proto.CreateRequest,
      io.grpc.examples.proto.CreateResponse> getCreateMethodHelper() {
    io.grpc.MethodDescriptor<io.grpc.examples.proto.CreateRequest, io.grpc.examples.proto.CreateResponse> getCreateMethod;
    if ((getCreateMethod = KeyValueServiceGrpc.getCreateMethod) == null) {
      synchronized (KeyValueServiceGrpc.class) {
        if ((getCreateMethod = KeyValueServiceGrpc.getCreateMethod) == null) {
          KeyValueServiceGrpc.getCreateMethod = getCreateMethod = 
              io.grpc.MethodDescriptor.<io.grpc.examples.proto.CreateRequest, io.grpc.examples.proto.CreateResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "io.grpc.KeyValueService", "Create"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.proto.CreateRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.proto.CreateResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new KeyValueServiceMethodDescriptorSupplier("Create"))
                  .build();
          }
        }
     }
     return getCreateMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getRetrieveMethod()} instead. 
  public static final io.grpc.MethodDescriptor<io.grpc.examples.proto.RetrieveRequest,
      io.grpc.examples.proto.RetrieveResponse> METHOD_RETRIEVE = getRetrieveMethodHelper();

  private static volatile io.grpc.MethodDescriptor<io.grpc.examples.proto.RetrieveRequest,
      io.grpc.examples.proto.RetrieveResponse> getRetrieveMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<io.grpc.examples.proto.RetrieveRequest,
      io.grpc.examples.proto.RetrieveResponse> getRetrieveMethod() {
    return getRetrieveMethodHelper();
  }

  private static io.grpc.MethodDescriptor<io.grpc.examples.proto.RetrieveRequest,
      io.grpc.examples.proto.RetrieveResponse> getRetrieveMethodHelper() {
    io.grpc.MethodDescriptor<io.grpc.examples.proto.RetrieveRequest, io.grpc.examples.proto.RetrieveResponse> getRetrieveMethod;
    if ((getRetrieveMethod = KeyValueServiceGrpc.getRetrieveMethod) == null) {
      synchronized (KeyValueServiceGrpc.class) {
        if ((getRetrieveMethod = KeyValueServiceGrpc.getRetrieveMethod) == null) {
          KeyValueServiceGrpc.getRetrieveMethod = getRetrieveMethod = 
              io.grpc.MethodDescriptor.<io.grpc.examples.proto.RetrieveRequest, io.grpc.examples.proto.RetrieveResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "io.grpc.KeyValueService", "Retrieve"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.proto.RetrieveRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.proto.RetrieveResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new KeyValueServiceMethodDescriptorSupplier("Retrieve"))
                  .build();
          }
        }
     }
     return getRetrieveMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getUpdateMethod()} instead. 
  public static final io.grpc.MethodDescriptor<io.grpc.examples.proto.UpdateRequest,
      io.grpc.examples.proto.UpdateResponse> METHOD_UPDATE = getUpdateMethodHelper();

  private static volatile io.grpc.MethodDescriptor<io.grpc.examples.proto.UpdateRequest,
      io.grpc.examples.proto.UpdateResponse> getUpdateMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<io.grpc.examples.proto.UpdateRequest,
      io.grpc.examples.proto.UpdateResponse> getUpdateMethod() {
    return getUpdateMethodHelper();
  }

  private static io.grpc.MethodDescriptor<io.grpc.examples.proto.UpdateRequest,
      io.grpc.examples.proto.UpdateResponse> getUpdateMethodHelper() {
    io.grpc.MethodDescriptor<io.grpc.examples.proto.UpdateRequest, io.grpc.examples.proto.UpdateResponse> getUpdateMethod;
    if ((getUpdateMethod = KeyValueServiceGrpc.getUpdateMethod) == null) {
      synchronized (KeyValueServiceGrpc.class) {
        if ((getUpdateMethod = KeyValueServiceGrpc.getUpdateMethod) == null) {
          KeyValueServiceGrpc.getUpdateMethod = getUpdateMethod = 
              io.grpc.MethodDescriptor.<io.grpc.examples.proto.UpdateRequest, io.grpc.examples.proto.UpdateResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "io.grpc.KeyValueService", "Update"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.proto.UpdateRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.proto.UpdateResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new KeyValueServiceMethodDescriptorSupplier("Update"))
                  .build();
          }
        }
     }
     return getUpdateMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getDeleteMethod()} instead. 
  public static final io.grpc.MethodDescriptor<io.grpc.examples.proto.DeleteRequest,
      io.grpc.examples.proto.DeleteResponse> METHOD_DELETE = getDeleteMethodHelper();

  private static volatile io.grpc.MethodDescriptor<io.grpc.examples.proto.DeleteRequest,
      io.grpc.examples.proto.DeleteResponse> getDeleteMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<io.grpc.examples.proto.DeleteRequest,
      io.grpc.examples.proto.DeleteResponse> getDeleteMethod() {
    return getDeleteMethodHelper();
  }

  private static io.grpc.MethodDescriptor<io.grpc.examples.proto.DeleteRequest,
      io.grpc.examples.proto.DeleteResponse> getDeleteMethodHelper() {
    io.grpc.MethodDescriptor<io.grpc.examples.proto.DeleteRequest, io.grpc.examples.proto.DeleteResponse> getDeleteMethod;
    if ((getDeleteMethod = KeyValueServiceGrpc.getDeleteMethod) == null) {
      synchronized (KeyValueServiceGrpc.class) {
        if ((getDeleteMethod = KeyValueServiceGrpc.getDeleteMethod) == null) {
          KeyValueServiceGrpc.getDeleteMethod = getDeleteMethod = 
              io.grpc.MethodDescriptor.<io.grpc.examples.proto.DeleteRequest, io.grpc.examples.proto.DeleteResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "io.grpc.KeyValueService", "Delete"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.proto.DeleteRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.proto.DeleteResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new KeyValueServiceMethodDescriptorSupplier("Delete"))
                  .build();
          }
        }
     }
     return getDeleteMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static KeyValueServiceStub newStub(io.grpc.Channel channel) {
    return new KeyValueServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static KeyValueServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new KeyValueServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static KeyValueServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new KeyValueServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class KeyValueServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void create(io.grpc.examples.proto.CreateRequest request,
        io.grpc.stub.StreamObserver<io.grpc.examples.proto.CreateResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getCreateMethodHelper(), responseObserver);
    }

    /**
     */
    public void retrieve(io.grpc.examples.proto.RetrieveRequest request,
        io.grpc.stub.StreamObserver<io.grpc.examples.proto.RetrieveResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRetrieveMethodHelper(), responseObserver);
    }

    /**
     */
    public void update(io.grpc.examples.proto.UpdateRequest request,
        io.grpc.stub.StreamObserver<io.grpc.examples.proto.UpdateResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getUpdateMethodHelper(), responseObserver);
    }

    /**
     */
    public void delete(io.grpc.examples.proto.DeleteRequest request,
        io.grpc.stub.StreamObserver<io.grpc.examples.proto.DeleteResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getDeleteMethodHelper(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getCreateMethodHelper(),
            asyncUnaryCall(
              new MethodHandlers<
                io.grpc.examples.proto.CreateRequest,
                io.grpc.examples.proto.CreateResponse>(
                  this, METHODID_CREATE)))
          .addMethod(
            getRetrieveMethodHelper(),
            asyncUnaryCall(
              new MethodHandlers<
                io.grpc.examples.proto.RetrieveRequest,
                io.grpc.examples.proto.RetrieveResponse>(
                  this, METHODID_RETRIEVE)))
          .addMethod(
            getUpdateMethodHelper(),
            asyncUnaryCall(
              new MethodHandlers<
                io.grpc.examples.proto.UpdateRequest,
                io.grpc.examples.proto.UpdateResponse>(
                  this, METHODID_UPDATE)))
          .addMethod(
            getDeleteMethodHelper(),
            asyncUnaryCall(
              new MethodHandlers<
                io.grpc.examples.proto.DeleteRequest,
                io.grpc.examples.proto.DeleteResponse>(
                  this, METHODID_DELETE)))
          .build();
    }
  }

  /**
   */
  public static final class KeyValueServiceStub extends io.grpc.stub.AbstractStub<KeyValueServiceStub> {
    private KeyValueServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private KeyValueServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected KeyValueServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new KeyValueServiceStub(channel, callOptions);
    }

    /**
     */
    public void create(io.grpc.examples.proto.CreateRequest request,
        io.grpc.stub.StreamObserver<io.grpc.examples.proto.CreateResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getCreateMethodHelper(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void retrieve(io.grpc.examples.proto.RetrieveRequest request,
        io.grpc.stub.StreamObserver<io.grpc.examples.proto.RetrieveResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRetrieveMethodHelper(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void update(io.grpc.examples.proto.UpdateRequest request,
        io.grpc.stub.StreamObserver<io.grpc.examples.proto.UpdateResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getUpdateMethodHelper(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void delete(io.grpc.examples.proto.DeleteRequest request,
        io.grpc.stub.StreamObserver<io.grpc.examples.proto.DeleteResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getDeleteMethodHelper(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class KeyValueServiceBlockingStub extends io.grpc.stub.AbstractStub<KeyValueServiceBlockingStub> {
    private KeyValueServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private KeyValueServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected KeyValueServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new KeyValueServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public io.grpc.examples.proto.CreateResponse create(io.grpc.examples.proto.CreateRequest request) {
      return blockingUnaryCall(
          getChannel(), getCreateMethodHelper(), getCallOptions(), request);
    }

    /**
     */
    public io.grpc.examples.proto.RetrieveResponse retrieve(io.grpc.examples.proto.RetrieveRequest request) {
      return blockingUnaryCall(
          getChannel(), getRetrieveMethodHelper(), getCallOptions(), request);
    }

    /**
     */
    public io.grpc.examples.proto.UpdateResponse update(io.grpc.examples.proto.UpdateRequest request) {
      return blockingUnaryCall(
          getChannel(), getUpdateMethodHelper(), getCallOptions(), request);
    }

    /**
     */
    public io.grpc.examples.proto.DeleteResponse delete(io.grpc.examples.proto.DeleteRequest request) {
      return blockingUnaryCall(
          getChannel(), getDeleteMethodHelper(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class KeyValueServiceFutureStub extends io.grpc.stub.AbstractStub<KeyValueServiceFutureStub> {
    private KeyValueServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private KeyValueServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected KeyValueServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new KeyValueServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.grpc.examples.proto.CreateResponse> create(
        io.grpc.examples.proto.CreateRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getCreateMethodHelper(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.grpc.examples.proto.RetrieveResponse> retrieve(
        io.grpc.examples.proto.RetrieveRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getRetrieveMethodHelper(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.grpc.examples.proto.UpdateResponse> update(
        io.grpc.examples.proto.UpdateRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getUpdateMethodHelper(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.grpc.examples.proto.DeleteResponse> delete(
        io.grpc.examples.proto.DeleteRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getDeleteMethodHelper(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE = 0;
  private static final int METHODID_RETRIEVE = 1;
  private static final int METHODID_UPDATE = 2;
  private static final int METHODID_DELETE = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final KeyValueServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(KeyValueServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CREATE:
          serviceImpl.create((io.grpc.examples.proto.CreateRequest) request,
              (io.grpc.stub.StreamObserver<io.grpc.examples.proto.CreateResponse>) responseObserver);
          break;
        case METHODID_RETRIEVE:
          serviceImpl.retrieve((io.grpc.examples.proto.RetrieveRequest) request,
              (io.grpc.stub.StreamObserver<io.grpc.examples.proto.RetrieveResponse>) responseObserver);
          break;
        case METHODID_UPDATE:
          serviceImpl.update((io.grpc.examples.proto.UpdateRequest) request,
              (io.grpc.stub.StreamObserver<io.grpc.examples.proto.UpdateResponse>) responseObserver);
          break;
        case METHODID_DELETE:
          serviceImpl.delete((io.grpc.examples.proto.DeleteRequest) request,
              (io.grpc.stub.StreamObserver<io.grpc.examples.proto.DeleteResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class KeyValueServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    KeyValueServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return io.grpc.examples.proto.KeyValues.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("KeyValueService");
    }
  }

  private static final class KeyValueServiceFileDescriptorSupplier
      extends KeyValueServiceBaseDescriptorSupplier {
    KeyValueServiceFileDescriptorSupplier() {}
  }

  private static final class KeyValueServiceMethodDescriptorSupplier
      extends KeyValueServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    KeyValueServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (KeyValueServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new KeyValueServiceFileDescriptorSupplier())
              .addMethod(getCreateMethodHelper())
              .addMethod(getRetrieveMethodHelper())
              .addMethod(getUpdateMethodHelper())
              .addMethod(getDeleteMethodHelper())
              .build();
        }
      }
    }
    return result;
  }
}
