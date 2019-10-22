package com.example.springdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalPlanDTO {

    private Integer id;
    private List<MedicationDTO> medications;
    private String intakeIntervals;
    private String timePeriod;
}
