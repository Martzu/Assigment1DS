package com.example.springdemo.services;


import com.example.springdemo.entities.MedicalPlan;
import com.example.springdemo.entities.Medication;
import com.example.springdemo.repositories.FactoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MedicationService {

    private final FactoryRepository factoryRepository;

    public Medication save(Medication medication)
    {
        return factoryRepository.createMedicationRepository().save(medication);
    }
}
