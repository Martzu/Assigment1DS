package com.example.gRPCClient;

import com.example.gRPCClient.grpc.medicalPlanGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.var;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

@SpringBootApplication
public class GRpcClientApplication{

	public static void main(String[] args) {

		SpringApplicationBuilder builder = new SpringApplicationBuilder(GRpcClientApplication.class);
		builder.headless(false);
		ConfigurableApplicationContext context = builder.run(args);



	}

}
