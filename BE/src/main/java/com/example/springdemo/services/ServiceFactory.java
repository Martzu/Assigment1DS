package com.example.springdemo.services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ServiceFactory {

    private final HospitalUserService hospitalUserService;
    private final MedicalPlanService medicalPlanService;
    private final MedicationService medicationService;

    public HospitalUserService createHospitalUserService()
    {
        return hospitalUserService;
    }

    public MedicalPlanService createMedicalPlanService()
    {
        return medicalPlanService;
    }

    public MedicationService createMedicationService()
    {
        return medicationService;
    }
}
