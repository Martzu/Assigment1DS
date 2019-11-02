package com.example.springdemo.seed;


import com.example.springdemo.entities.Sensor;
import com.example.springdemo.repositories.FactoryRepository;
import com.example.springdemo.services.HospitalUserService;
import com.example.springdemo.services.MedicalPlanService;
import com.example.springdemo.services.MedicationService;
import com.google.gson.Gson;
import com.google.gson.JsonParser;

import com.rabbitmq.client.*;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Priority;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Channel;

@Component
@RequiredArgsConstructor
//@Order(Ordered.HIGHEST_PRECEDENCE)
public class Seed implements CommandLineRunner {

    private final HospitalUserService hospitalUserService;
    private final MedicalPlanService medicalPlanService;
    private final MedicationService medicationService;

    private final FactoryRepository factoryRepository;
    @Override
    @Transactional
    public void run(String... args) throws Exception {

        /*HospitalUser doctor1 = new HospitalUser("Alex", "M", "Cluj", "Alex", "parola", "12.05.1990",HospitalUserRoles.MEDIC);

        HospitalUser patient1 = new HospitalUser("Andrei", "M", "Cluj", "Andrei", "parola", "12.05.1990",HospitalUserRoles.PATIENT);
        HospitalUser patient2 = new HospitalUser( "Andreea", "F", "Cluj", "Andreea", "parola", "12.05.1990",HospitalUserRoles.PATIENT);
        HospitalUser patient3 = new HospitalUser( "Ioana", "F", "Cluj", "Ioana", "parola", "12.05.1990",HospitalUserRoles.PATIENT);

        List<HospitalUser> patients = new ArrayList<>();
        patients.add(patient1);
        patients.add(patient2);
        patients.add(patient3);

        HospitalUser careTaker1 = new HospitalUser("Mihaela", "F", "Cluj", "Mihaela", "parola", "13.02.1997",HospitalUserRoles.CARETAKER, patients);

        hospitalUserService.save(doctor1);
        hospitalUserService.save(patient1);
        hospitalUserService.save(patient2);
        hospitalUserService.save(patient3);
        hospitalUserService.save(careTaker1);

        hospitalUserService.getAllByRole(HospitalUserRoles.PATIENT).forEach(System.out::println);

        hospitalUserService.getAllPatientsOfCaretaker("Mihaela").forEach(System.out::println);

        HospitalUser patient4 = new HospitalUser( "Ion", "M", "Suceava", "Ion", "parola", "12.05.1990",HospitalUserRoles.PATIENT);

        hospitalUserService.assignPatientToCaretaker(patient4, hospitalUserService.getCaretakerByName("Mihaela"));

        hospitalUserService.getAllPatientsOfCaretaker("Mihaela").forEach(System.out::println);

        Medication medication1 = new Medication("Amphetamine", "Nausea, hallucinations", "20ml");
        Medication medication2 = new Medication("Morphine", "Hallucinations, possible suffocation", "5ml");

        medicationService.save(medication1);
        medicationService.save(medication2);

        List<Medication> medications = new ArrayList<>();
        medications.add(medication1);
        medications.add(medication2);

        HospitalUser doctor = hospitalUserService.findById(1).orElse(new HospitalUser());

        MedicalPlan medicalPlan = new MedicalPlan(patient4, doctor, medications, "3 weeks", "Amphetamine 5ml morning, Morphine 1 ml evening");

        medicalPlanService.save(medicalPlan);

        medicalPlanService.findMedicalPlansOfPatient(patient4).forEach(System.out::println);

        HospitalUser medic = hospitalUserService.verifyUserCredentials("Alex", "parola");

        System.out.println(medic.getRole());*/

        /*HospitalUser patient = new HospitalUser("Andreea", "F", "Bucuresti", "deea","parola", "29.04.1997",HospitalUserRoles.PATIENT);
        HospitalUser caretaker = hospitalUserService.findById(8).get();
        hospitalUserService.assignPatientToCaretaker(patient,caretaker);*/

        //No need to create manually queue in manager, it will be created automatically

        /* SENDER
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try(Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()){
            channel.queueDeclare("middleware", false, false, false, null);
            String message = "Hello world!";
            channel.basicPublish("", "middleware", null, message.getBytes());
            System.out.println("Sent " + message);
        }*/


        //RECEIVER
        /*
        ConnectionFactory factory1 = new ConnectionFactory();
        factory1.setHost("localhost");
        Connection connection1 = factory1.newConnection();
        Channel channel1 = connection1.createChannel();

        channel1.queueDeclare("middleware", false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");

            //Object object = new JsonParser().parse(message);
            Sensor receivedSensor = new Gson().fromJson(message, Sensor.class);
            System.out.println(" [x] Received '" + receivedSensor.toString() + "'");
        };
        channel1.basicConsume("middleware", true, deliverCallback, consumerTag -> { });*/

        //factoryRepository.createSensorRepository().findAll().forEach(sensor -> System.out.println(sensor.toString() + " din findAll"));

    }
}
