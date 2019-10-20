package com.example.springdemo.services;


import com.example.springdemo.entities.MedicalPlan;
import com.example.springdemo.entities.Medication;
import com.example.springdemo.repositories.FactoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MedicationService {

    private final FactoryRepository factoryRepository;

    public Medication save(Medication medication)
    {
        return factoryRepository.createMedicationRepository().save(medication);
    }

    public Optional<Medication> findById(Integer id)
    {
        return factoryRepository.createMedicationRepository().findById(id);
    }

    public void removeMedication(Medication medication)
    {
        factoryRepository.createMedicationRepository().delete(medication);
    }

    public List<Medication> findAllMedications()
    {
        return  factoryRepository.createMedicationRepository().findAll();
    }

}
