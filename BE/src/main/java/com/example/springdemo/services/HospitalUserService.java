package com.example.springdemo.services;


import com.example.springdemo.entities.HospitalUser;
import com.example.springdemo.entities.HospitalUserRoles;
import com.example.springdemo.entities.MedicalPlan;
import com.example.springdemo.repositories.FactoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HospitalUserService {

    private final FactoryRepository factoryRepository;


    public HospitalUser verifyUserCredentials(String username, String password) throws Exception {
        return factoryRepository.createHospitalUserRepository().findHospitalUserByUsernameAndPassword(username, password).orElseThrow(() ->new Exception("No user found!"));
    }

    public void removeHospitalUserById(Integer id)
    {
        factoryRepository.createHospitalUserRepository().removeHospitalUserById(id);
    }

    public void removeHospitalUser(HospitalUser user)
    {
        factoryRepository.createHospitalUserRepository().delete(user);
    }

    public Optional<HospitalUser> findById(int id)
    {
        return factoryRepository.createHospitalUserRepository().findById(id);
    }

    public HospitalUser save(HospitalUser hospitalUser)
    {
        return factoryRepository.createHospitalUserRepository().save(hospitalUser);
    }

    public HospitalUser getCaretakerByName(String name)
    {
        return factoryRepository.createHospitalUserRepository().findHospitalUserByNameAndRole(name, HospitalUserRoles.CARETAKER);
    }

    public List<HospitalUser> getAllHospitalUsers()
    {
        return factoryRepository.createHospitalUserRepository().findAll();
    }

    public List<HospitalUser> getAllByRole(String role)
    {
        return factoryRepository.createHospitalUserRepository().findByRole(role);
    }

    public List<HospitalUser> getAllPatientsOfCaretaker(String name)
    {
        List<HospitalUser> caretaker = new ArrayList<>();
        caretaker.add(factoryRepository.createHospitalUserRepository().findHospitalUserByNameAndRole(name, HospitalUserRoles.CARETAKER));
        return factoryRepository.createHospitalUserRepository().findHospitalUsersByCaretakers(caretaker);
    }

    public void assignPatientToCaretaker(HospitalUser patient, HospitalUser caretaker)
    {
        patient.getCaretakers().add(caretaker);
        caretaker.getPatients().add(patient);
        factoryRepository.createHospitalUserRepository().save(patient);
        factoryRepository.createHospitalUserRepository().save(caretaker);
    }

    public List<HospitalUser> findDoctorsOfPatient(HospitalUser patient)
    {
        return factoryRepository.createMedicalPlanRepository().findMedicalPlansByPatient(patient).stream().map(MedicalPlan::getDoctor).collect(Collectors.toList());
    }



}
