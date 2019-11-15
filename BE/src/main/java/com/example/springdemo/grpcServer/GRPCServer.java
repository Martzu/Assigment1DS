package com.example.springdemo.grpcServer;

import com.example.springdemo.grpcService.MedicalPlanGRPCService;
import com.example.springdemo.repositories.FactoryRepository;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GRPCServer implements CommandLineRunner {
    private final FactoryRepository factoryRepository;
    @Override
    public void run(String... args) throws Exception {
        Server server = ServerBuilder.forPort(9090).addService(new MedicalPlanGRPCService(factoryRepository)).build();
        server.start();
        System.out.println("works at "+ server.getPort());
        server.awaitTermination();
    }
}
