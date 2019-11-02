package com.example.springdemo.rabbitMq;

import com.example.springdemo.dto.PatientProblemDTO;
import com.example.springdemo.entities.Sensor;
import com.example.springdemo.event.BaseEvent;
import com.example.springdemo.event.SensorAnomalyEvent;
import com.example.springdemo.repositories.FactoryRepository;
import com.google.gson.Gson;
import com.rabbitmq.client.DeliverCallback;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import com.rabbitmq.client.*;

@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RabbitMqReceiver implements CommandLineRunner {

    private final FactoryRepository factoryRepository;

    private final SimpMessagingTemplate messagingTemplate;

    private final ApplicationEventPublisher eventPublisher;

    private boolean timeLimitPassed(Sensor sensor, boolean twelveHours)
    {
        //cut 3 0's
        int timeInMillis = twelveHours ? 43200 : 3600;
        return Long.parseLong(sensor.getEnd()) - Long.parseLong(sensor.getStart()) > timeInMillis;
    }


    private boolean checkAnomaly(Sensor sensor)
    {
        boolean anomalyDetected = false;
        switch (sensor.getActivity())
        {
            case "Sleeping":
            case "Leaving": {
                if(timeLimitPassed(sensor, true))
                {
                    anomalyDetected = true;
                }
                break;
            }
            case "Toileting":
            case "Showering":
            case "Grooming":
            {
                if(timeLimitPassed(sensor,false))
                {
                    anomalyDetected = true;
                }
                break;
            }
        }
        return anomalyDetected;
    }


    @Override
    public void run(String... args) throws Exception {
        ConnectionFactory factory1 = new ConnectionFactory();
        factory1.setHost("localhost");
        Connection connection1 = factory1.newConnection();
        Channel channel1 = connection1.createChannel();

        channel1.queueDeclare("middleware", false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            Sensor receivedSensor = new Gson().fromJson(message, Sensor.class);
            factoryRepository.createSensorRepository().save(receivedSensor);
            System.out.println(" [x] Received '" + receivedSensor.toString() + "'");
            if(checkAnomaly(receivedSensor))
            {
                PatientProblemDTO warning = new PatientProblemDTO(receivedSensor.getPatientId(), receivedSensor.getActivity());
                eventPublisher.publishEvent(new SensorAnomalyEvent(warning));
            }
        };
        channel1.basicConsume("middleware", true, deliverCallback, consumerTag -> { });
    }

    @EventListener(BaseEvent.class)
    public void handleSensorAnomaly(BaseEvent event)
    {
        System.out.println("Got it " + event);
        messagingTemplate.convertAndSend("/topic/events", event);
    }
}
