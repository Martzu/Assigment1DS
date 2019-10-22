package com.example.springdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Session {
    private Integer sessionId;
    private Integer idToDelete;
    private Integer idToUpdate;
    private String role;
    private String name;
    private String address;
    private String birthDate;
    private String gender;

    private String medicationName;
    private String sideEffects;
    private String dosage;

    private String timePeriod;
    private String intakeIntervals;

    private List<Integer> medicalPlanMedicationsId;
    private Integer patientId;

    public Session(Integer id, String role)
    {
        this.sessionId = id;
        this.role = role;
    }
}
