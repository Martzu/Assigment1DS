package com.example.springdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MedicationDTO {
    private Integer id;
    private String medicationName;
    private String sideEffects;
    private String dosage;
}
