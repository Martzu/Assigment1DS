package com.example.springdemo.repositories;

import com.example.springdemo.entities.Medication;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

public interface MedicationRepository extends CRUDRepository<Medication>, Repository<Medication, Integer> {
}
