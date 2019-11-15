package com.example.gRPCClient.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.15.0)",
    comments = "Source: medicalPlan.proto")
public final class medicalPlanGrpc {

  private medicalPlanGrpc() {}

  public static final String SERVICE_NAME = "medicalPlan";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<MedicalPlan.ClientRequest,
          MedicalPlan.MedicalPlansResponse> getDownloadMedicalPlanMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "downloadMedicalPlan",
      requestType = MedicalPlan.ClientRequest.class,
      responseType = MedicalPlan.MedicalPlansResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<MedicalPlan.ClientRequest,
          MedicalPlan.MedicalPlansResponse> getDownloadMedicalPlanMethod() {
    io.grpc.MethodDescriptor<MedicalPlan.ClientRequest, MedicalPlan.MedicalPlansResponse> getDownloadMedicalPlanMethod;
    if ((getDownloadMedicalPlanMethod = medicalPlanGrpc.getDownloadMedicalPlanMethod) == null) {
      synchronized (medicalPlanGrpc.class) {
        if ((getDownloadMedicalPlanMethod = medicalPlanGrpc.getDownloadMedicalPlanMethod) == null) {
          medicalPlanGrpc.getDownloadMedicalPlanMethod = getDownloadMedicalPlanMethod = 
              io.grpc.MethodDescriptor.<MedicalPlan.ClientRequest, MedicalPlan.MedicalPlansResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "medicalPlan", "downloadMedicalPlan"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  MedicalPlan.ClientRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  MedicalPlan.MedicalPlansResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new medicalPlanMethodDescriptorSupplier("downloadMedicalPlan"))
                  .build();
          }
        }
     }
     return getDownloadMedicalPlanMethod;
  }

  private static volatile io.grpc.MethodDescriptor<MedicalPlan.Medication,
          MedicalPlan.Empty> getMedicationTakenMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "medicationTaken",
      requestType = MedicalPlan.Medication.class,
      responseType = MedicalPlan.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<MedicalPlan.Medication,
          MedicalPlan.Empty> getMedicationTakenMethod() {
    io.grpc.MethodDescriptor<MedicalPlan.Medication, MedicalPlan.Empty> getMedicationTakenMethod;
    if ((getMedicationTakenMethod = medicalPlanGrpc.getMedicationTakenMethod) == null) {
      synchronized (medicalPlanGrpc.class) {
        if ((getMedicationTakenMethod = medicalPlanGrpc.getMedicationTakenMethod) == null) {
          medicalPlanGrpc.getMedicationTakenMethod = getMedicationTakenMethod = 
              io.grpc.MethodDescriptor.<MedicalPlan.Medication, MedicalPlan.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "medicalPlan", "medicationTaken"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  MedicalPlan.Medication.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  MedicalPlan.Empty.getDefaultInstance()))
                  .setSchemaDescriptor(new medicalPlanMethodDescriptorSupplier("medicationTaken"))
                  .build();
          }
        }
     }
     return getMedicationTakenMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static medicalPlanStub newStub(io.grpc.Channel channel) {
    return new medicalPlanStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static medicalPlanBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new medicalPlanBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static medicalPlanFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new medicalPlanFutureStub(channel);
  }

  /**
   */
  public static abstract class medicalPlanImplBase implements io.grpc.BindableService {

    /**
     */
    public void downloadMedicalPlan(MedicalPlan.ClientRequest request,
                                    io.grpc.stub.StreamObserver<MedicalPlan.MedicalPlansResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getDownloadMedicalPlanMethod(), responseObserver);
    }

    /**
     */
    public void medicationTaken(MedicalPlan.Medication request,
                                io.grpc.stub.StreamObserver<MedicalPlan.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getMedicationTakenMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getDownloadMedicalPlanMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                      MedicalPlan.ClientRequest,
                      MedicalPlan.MedicalPlansResponse>(
                  this, METHODID_DOWNLOAD_MEDICAL_PLAN)))
          .addMethod(
            getMedicationTakenMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                      MedicalPlan.Medication,
                      MedicalPlan.Empty>(
                  this, METHODID_MEDICATION_TAKEN)))
          .build();
    }
  }

  /**
   */
  public static final class medicalPlanStub extends io.grpc.stub.AbstractStub<medicalPlanStub> {
    private medicalPlanStub(io.grpc.Channel channel) {
      super(channel);
    }

    private medicalPlanStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected medicalPlanStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new medicalPlanStub(channel, callOptions);
    }

    /**
     */
    public void downloadMedicalPlan(MedicalPlan.ClientRequest request,
                                    io.grpc.stub.StreamObserver<MedicalPlan.MedicalPlansResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getDownloadMedicalPlanMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void medicationTaken(MedicalPlan.Medication request,
                                io.grpc.stub.StreamObserver<MedicalPlan.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getMedicationTakenMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class medicalPlanBlockingStub extends io.grpc.stub.AbstractStub<medicalPlanBlockingStub> {
    private medicalPlanBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private medicalPlanBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected medicalPlanBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new medicalPlanBlockingStub(channel, callOptions);
    }

    /**
     */
    public MedicalPlan.MedicalPlansResponse downloadMedicalPlan(MedicalPlan.ClientRequest request) {
      return blockingUnaryCall(
          getChannel(), getDownloadMedicalPlanMethod(), getCallOptions(), request);
    }

    /**
     */
    public MedicalPlan.Empty medicationTaken(MedicalPlan.Medication request) {
      return blockingUnaryCall(
          getChannel(), getMedicationTakenMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class medicalPlanFutureStub extends io.grpc.stub.AbstractStub<medicalPlanFutureStub> {
    private medicalPlanFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private medicalPlanFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected medicalPlanFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new medicalPlanFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<MedicalPlan.MedicalPlansResponse> downloadMedicalPlan(
        MedicalPlan.ClientRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getDownloadMedicalPlanMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<MedicalPlan.Empty> medicationTaken(
        MedicalPlan.Medication request) {
      return futureUnaryCall(
          getChannel().newCall(getMedicationTakenMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_DOWNLOAD_MEDICAL_PLAN = 0;
  private static final int METHODID_MEDICATION_TAKEN = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final medicalPlanImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(medicalPlanImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_DOWNLOAD_MEDICAL_PLAN:
          serviceImpl.downloadMedicalPlan((MedicalPlan.ClientRequest) request,
              (io.grpc.stub.StreamObserver<MedicalPlan.MedicalPlansResponse>) responseObserver);
          break;
        case METHODID_MEDICATION_TAKEN:
          serviceImpl.medicationTaken((MedicalPlan.Medication) request,
              (io.grpc.stub.StreamObserver<MedicalPlan.Empty>) responseObserver);
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

  private static abstract class medicalPlanBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    medicalPlanBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return MedicalPlan.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("medicalPlan");
    }
  }

  private static final class medicalPlanFileDescriptorSupplier
      extends medicalPlanBaseDescriptorSupplier {
    medicalPlanFileDescriptorSupplier() {}
  }

  private static final class medicalPlanMethodDescriptorSupplier
      extends medicalPlanBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    medicalPlanMethodDescriptorSupplier(String methodName) {
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
      synchronized (medicalPlanGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new medicalPlanFileDescriptorSupplier())
              .addMethod(getDownloadMedicalPlanMethod())
              .addMethod(getMedicationTakenMethod())
              .build();
        }
      }
    }
    return result;
  }
}
