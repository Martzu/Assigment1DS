package com.example.demo.producer;

import org.json.simple.JSONObject;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

@Component
public class Producer implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try(Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()){
            channel.queueDeclare("middleware", false, false, false, null);
            Stream<String> stream = Files.lines(Paths.get("/home/martzu/Desktop/demo/src/main/java/com/example/demo/data.txt"));
            stream.forEach(data -> {
                try {
                    //split by tabs, get 0 1 2
                    String info[] = data.split("\t\t");
                    LocalDateTime startDate = LocalDateTime.parse(info[0], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    LocalDateTime endDate = LocalDateTime.parse(info[1], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    long startDateMillis = startDate.toEpochSecond(ZoneOffset.UTC);
                    long endDateMillis = endDate.toEpochSecond(ZoneOffset.UTC);

                    JSONObject object = new JSONObject();
                    object.put("patientId", "6");
                    object.put("start", Long.toString(startDateMillis));
                    object.put("end", Long.toString(endDateMillis));
                    object.put("activity", info[2]);
                    channel.basicPublish("", "middleware",null, object.toString().getBytes());
                    Thread.sleep(4000);
                    System.out.println("Send: " + object.toString());
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
