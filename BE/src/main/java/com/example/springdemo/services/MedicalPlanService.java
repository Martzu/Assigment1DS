package com.example.springdemo.services;

import com.example.springdemo.entities.HospitalUser;
import com.example.springdemo.entities.MedicalPlan;
import com.example.springdemo.entities.Medication;
import com.example.springdemo.repositories.FactoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class MedicalPlanService {

    private final FactoryRepository factoryRepository;

    public MedicalPlan save(MedicalPlan medicalPlan)
    {
        return factoryRepository.createMedicalPlanRepository().save(medicalPlan);
    }

    public MedicalPlan assignMedicationToMedicalPlan(Medication medication, MedicalPlan medicalPlan)
    {
        medicalPlan.getMedications().add(medication);
        return factoryRepository.createMedicalPlanRepository().save(medicalPlan);
    }

    public List<MedicalPlan> findMedicalPlansOfPatient(HospitalUser patient)
    {
        return factoryRepository.createMedicalPlanRepository().findMedicalPlansByPatient(patient);
    }



}
