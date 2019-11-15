package com.example.gRPCClient.client;

import com.example.gRPCClient.grpc.MedicalPlan;
import com.example.gRPCClient.grpc.medicalPlanGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.Timer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.*;


@Component
public class GRPCClient implements CommandLineRunner {

    private String[] medications;

    private JButton test = new JButton("test");

    boolean testB = false;

    @Override
    public void run(String... args) throws Exception {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();

        medicalPlanGrpc.medicalPlanBlockingStub stub = medicalPlanGrpc.newBlockingStub(channel);

        MedicalPlan.ClientRequest request = MedicalPlan.ClientRequest.newBuilder().setMedicalPlanDay("1").setPatientId("10").build();

        MedicalPlan.MedicalPlansResponse response = stub.downloadMedicalPlan(request);

        Integer day = 1;


        JFrame frame = new JFrame("Hello Patient");


        Map<String, Boolean> medicationsTaken = new HashMap<>();

        updateTime(frame);
        updateUI(frame);
        while(true)
        {
            medicationsTaken = checkIfDownloadTime(frame,0, stub, medicationsTaken, day);
            checkIfMedicationTimeExpired(medicationsTaken, stub);
            //removeButton(frame);
        }
    }

    private void updateUI(JFrame frame)
    {
        Timer t = new javax.swing.Timer(1000, actionEvent -> {
            frame.revalidate();
            frame.repaint();
        });
        t.start();
    }

    private void removeButton(JFrame frame)
    {
        if(testB)
        {
            frame.remove(test);
        }
    }

    private void updateTime(JFrame frame)
    {

        frame.setSize(500, 400);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridy = 7;
        constraints.gridx = 3;
        JLabel currentTime = new JLabel();
        frame.getContentPane().add(currentTime, constraints);
        constraints.gridy = 8;
        frame.getContentPane().add(test, constraints);
        Timer t = new javax.swing.Timer(1000, actionEvent -> currentTime.setText(LocalDateTime.now().toLocalTime().toString()));
        t.start();
    }

    private Map<String, Boolean> checkIfDownloadTime(JFrame frame, int time, medicalPlanGrpc.medicalPlanBlockingStub stub, Map<String, Boolean> taken, Integer day)
    {
        Map<String, Boolean> takenMedicine;
        if(LocalDateTime.now().getSecond() == time)
        {
            MedicalPlan.ClientRequest request = MedicalPlan.ClientRequest.newBuilder().setMedicalPlanDay(day.toString()).setPatientId("10").build();
            MedicalPlan.MedicalPlansResponse response = stub.downloadMedicalPlan(request);
            medications = response.getIntakeIntervals().split(",");
            takenMedicine = startUI(frame, medications, stub);
        }
        else
        {
            takenMedicine = taken;
        }
        return takenMedicine;
    }

    private void checkIfMedicationTimeExpired(Map<String, Boolean> medications, medicalPlanGrpc.medicalPlanBlockingStub stub)
    {
        medications.keySet().forEach(medication -> {
            String[] data = medication.split(":");
            String[] intervals = data[1].split("-");
            if(Integer.parseInt(intervals[1]) < LocalDateTime.now().getSecond() && medications.get(medication) == Boolean.FALSE)
            {
                medications.put(medication, Boolean.TRUE);
                informServerAboutMedication(stub, medication + " EXPIRED!");
            }
        });
    }

    private Map<String, Boolean> startUI(JFrame frame, String[] medications, medicalPlanGrpc.medicalPlanBlockingStub stub)
    {

        GridBagConstraints constraints = new GridBagConstraints();

        Map<JButton, JLabel> taken = new HashMap<>();
        Map<String, Boolean> takenMedications = new HashMap<>();

        for(int i = 0; i < medications.length; i++)
        {
            constraints.gridy = i;
            constraints.gridx = 1;
            JButton button = new JButton("Taken");
            final String wrapper = medications[i];

            button.addActionListener(e -> {
                informServerAboutMedication(stub, wrapper);
                frame.remove(test);
                removeMedicationFromUI(frame, button, taken.get(button));

                testB = true;
                taken.remove(button);
                takenMedications.put(wrapper, Boolean.TRUE);

            });

            frame.getContentPane().add(button, constraints);
            constraints.gridx= 0;

            JLabel label = new JLabel(wrapper);
            takenMedications.put(wrapper, Boolean.FALSE);
            frame.getContentPane().add(label, constraints);

            taken.put(button, label);
        }

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        updateUI(frame);

        return takenMedications;
    }

    private void informServerAboutMedication(medicalPlanGrpc.medicalPlanBlockingStub stub, String info)
    {
        MedicalPlan.Medication takenRequest = MedicalPlan.Medication.newBuilder().setName(info).build();
        MedicalPlan.Empty response = stub.medicationTaken(takenRequest);
    }

    private void removeMedicationFromUI(JFrame frame, JButton button, JLabel label)
    {
        frame.remove(button);
        frame.remove(label);
        frame.revalidate();
        frame.repaint();
    }

}
