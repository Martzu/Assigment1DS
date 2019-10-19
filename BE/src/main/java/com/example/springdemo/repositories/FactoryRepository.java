package com.example.springdemo.repositories;

public interface FactoryRepository {

    public HospitalUserRepository createHospitalUserRepository();

    public MedicationRepository createMedicationRepository();

    public MedicalPlanRepository createMedicalPlanRepository();
}
