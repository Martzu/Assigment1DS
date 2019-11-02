package com.example.springdemo.controller;


import com.example.springdemo.dto.HospitalUserDTO;
import com.example.springdemo.dto.MedicalPlanDTO;
import com.example.springdemo.dto.MedicationDTO;
import com.example.springdemo.entities.HospitalUser;
import com.example.springdemo.entities.HospitalUserRoles;
import com.example.springdemo.entities.MedicalPlan;
import com.example.springdemo.entities.Medication;
import com.example.springdemo.dto.Session;
import com.example.springdemo.event.BaseEvent;
import com.example.springdemo.services.ServiceFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@CrossOrigin
@RestController
@RequiredArgsConstructor
public class HospitalUserController {


    private Map<String, Boolean> connectedSessions = new ConcurrentHashMap<>();
    private final ServiceFactory serviceFactory;

    @PostMapping("/login")
    public Session authenticate(@RequestBody String credentials) throws Exception
    {
        String[] userData = new String[2];
        int index = 0;
        Matcher matcher = Pattern.compile("\\\"(username|password)\\\"\\:\\\"(\\w+)").matcher(credentials);
        while(matcher.find())
        {
            userData[index++] = matcher.group(2);
        }
        System.out.println(userData[0] + " " + userData[1]);
        HospitalUser user = serviceFactory.createHospitalUserService().verifyUserCredentials(userData[0], userData[1]);
        return new Session(user.getId(), user.getRole());


    }

    @PostMapping("/patient")
    public void createPatient(@RequestBody Session session)
    {
        if(isDoctorLoggedIn(session))
        {
            addUser(session);
        }
    }

    @DeleteMapping("/patient")
    public void deletePatient(@RequestBody Session session)
    {
        if(isDoctorLoggedIn(session))
        {
            if (serviceFactory.createHospitalUserService().findById(session.getIdToDelete()).isPresent())
            {
                serviceFactory.createHospitalUserService().removeHospitalUser(serviceFactory.createHospitalUserService().findById(session.getIdToDelete()).get());
            }
        }
    }

    @PutMapping("/patient")
    public void updatePatient(@RequestBody Session session) throws Exception
    {
        updateUser(session);
    }


    @PostMapping("/patients")
    public List<HospitalUserDTO> getPatientsOfDoctor(@RequestBody Session session)
    {
        Optional<HospitalUser> user = serviceFactory.createHospitalUserService().findById(session.getSessionId());
        List<HospitalUserDTO> patients = new ArrayList<>();
        if(user.isPresent() && user.get().getRole().equals(HospitalUserRoles.MEDIC)) {
            patients = serviceFactory.createHospitalUserService().getAllByRole(HospitalUserRoles.PATIENT).stream().map(patient -> new HospitalUserDTO(patient.getId(), patient.getName(), patient.getAddress(), patient.getGender(), patient.getBirthDate())).collect(Collectors.toList());
        }
        if(user.isPresent() && user.get().getRole().equals(HospitalUserRoles.CARETAKER))
        {
            patients = serviceFactory.createHospitalUserService().getAllPatientsOfCaretaker(user.get().getName()).stream().map(patient -> new HospitalUserDTO(patient.getId(), patient.getName(), patient.getAddress(), patient.getGender(), patient.getBirthDate())).collect(Collectors.toList());
        }
        return patients;
    }

    @PostMapping("/caretaker")
    public void createCaretaker(@RequestBody Session session)
    {
        if(isDoctorLoggedIn(session))
        {
            addUser(session);
        }
    }

    @DeleteMapping("/caretaker")
    public void deleteCaretaker(@RequestBody Session session)
    {
        if(isDoctorLoggedIn(session))
        {
            if(serviceFactory.createHospitalUserService().findById(session.getIdToDelete()).isPresent())
            {
                serviceFactory.createHospitalUserService().removeHospitalUser(serviceFactory.createHospitalUserService().findById(session.getIdToDelete()).get());
            }
        }
    }

    @PutMapping("/caretaker")
    public void updateCaretaker(@RequestBody Session session) throws Exception
    {
        updateUser(session);
    }

    @PostMapping("/caretakers")
    public List<HospitalUserDTO> getCaretakers(@RequestBody Session session)
    {
        List<HospitalUserDTO> caretakers = new ArrayList<>();
        if(isDoctorLoggedIn(session))
        {
            caretakers = serviceFactory.createHospitalUserService().getAllByRole(HospitalUserRoles.CARETAKER).stream().map(patient -> new HospitalUserDTO(patient.getId(), patient.getName(), patient.getAddress(), patient.getGender(), patient.getBirthDate())).collect(Collectors.toList());
        }
        return caretakers;
    }


    @PostMapping("/medication")
    public void createMedication(@RequestBody Session session)
    {
        if(isDoctorLoggedIn(session))
        {
            serviceFactory.createMedicationService().save(new Medication(session.getMedicationName(), session.getSideEffects(), session.getDosage()));
        }
    }

    @PutMapping("/medication")
    public void updateMedication(@RequestBody Session session) throws Exception
    {
        if(isDoctorLoggedIn(session))
        {
            Medication updatedMedication = serviceFactory.createMedicationService().findById(session.getIdToUpdate()).orElseThrow(() -> new Exception("Medication not found"));
            if(!session.getMedicationName().isEmpty())
            {
                updatedMedication.setName(session.getMedicationName());
            }
            if(!session.getDosage().isEmpty())
            {
                updatedMedication.setDosage(session.getDosage());
            }
            if(!session.getSideEffects().isEmpty())
            {
                updatedMedication.setSideEffects(session.getSideEffects());
            }
            serviceFactory.createMedicationService().save(updatedMedication);
        }
    }

    @DeleteMapping("/medication")
    public void deleteMedication(@RequestBody Session session) throws Exception
    {
        if(isDoctorLoggedIn(session))
        {
            Medication medicationToDelete = serviceFactory.createMedicationService().findById(session.getIdToDelete()).orElseThrow(() -> new Exception("Medicine not found"));
            serviceFactory.createMedicationService().removeMedication(medicationToDelete);
        }
    }

    @PostMapping("/medications")
    public List<MedicationDTO> getMedications(@RequestBody Session session)
    {
        List<MedicationDTO> medications = new ArrayList<>();
        if(isDoctorLoggedIn(session))
        {
            medications = serviceFactory.createMedicationService().findAllMedications().stream().map(medication -> new MedicationDTO(medication.getId(), medication.getName(), medication.getSideEffects(), medication.getDosage())).collect(Collectors.toList());
        }
        return medications;
    }

    @PostMapping("/medicalPlan")
    public void createMedicalPlan(@RequestBody Session session)
    {
        //can access optional directly with .get since in front-end the button is placed only on existing components
        if(isDoctorLoggedIn(session))
        {
            HospitalUser patient = serviceFactory.createHospitalUserService().findById(session.getPatientId()).get();
            //doctor will need to be taken accordingly to the sessionId generated by spring security
            HospitalUser doctor = serviceFactory.createHospitalUserService().findById(session.getSessionId()).get();
            List<Medication> planMedications = new ArrayList<>();
            session.getMedicalPlanMedicationsId().forEach(medicationId -> {
                planMedications.add(serviceFactory.createMedicationService().findById(medicationId).get());
            });
            serviceFactory.createMedicalPlanService().save(new MedicalPlan(patient, doctor, planMedications, session.getTimePeriod(), session.getIntakeIntervals()));
        }
    }

    @PostMapping("/medicalPlans")
    public List<MedicalPlanDTO> getPlans(@RequestBody Session session)
    {
        //if patient doesnt exist, will return empty list
        List<MedicalPlanDTO> medicalPlans = new ArrayList<>();
        if(isPatientLoggedIn(session))
        {
            List<MedicalPlan> medicalPlanList = serviceFactory.createMedicalPlanService().findMedicalPlansOfPatient(serviceFactory.createHospitalUserService().findById(session.getSessionId()).get());
            medicalPlanList.forEach(medicalPlan -> {
                List<MedicationDTO> medicationDTOS = medicalPlan.getMedications().stream().map(medication -> new MedicationDTO(medication.getId(), medication.getName(), medication.getSideEffects(), medication.getDosage())).collect(Collectors.toList());
                medicalPlans.add(new MedicalPlanDTO(medicalPlan.getId(), medicationDTOS, medicalPlan.getIntakeIntervals(), medicalPlan.getTimePeriod()));
            });
        }
        return medicalPlans;
    }

    private boolean isPatientLoggedIn(Session session)
    {
        Optional<HospitalUser> patient = serviceFactory.createHospitalUserService().findById(session.getSessionId());
        return patient.isPresent() && patient.get().getRole().equals(HospitalUserRoles.PATIENT);
    }

    private boolean isDoctorLoggedIn(Session session)
    {
        Optional<HospitalUser> doctor = serviceFactory.createHospitalUserService().findById(session.getSessionId());
        return doctor.isPresent() && doctor.get().getRole().equals(HospitalUserRoles.MEDIC);
    }


    private void updateUser(Session session) throws Exception
    {
        if(isDoctorLoggedIn(session))
        {
            HospitalUser patient = serviceFactory.createHospitalUserService().findById(session.getIdToUpdate()).orElseThrow(() -> new Exception("Patient does not exist!"));
            if(!session.getName().isEmpty())
            {
                patient.setName(session.getName());
            }
            if(!session.getAddress().isEmpty())
            {
                patient.setAddress(session.getAddress());
            }
            if(!session.getBirthDate().isEmpty())
            {
                patient.setBirthDate(session.getBirthDate());
            }
            if(!session.getGender().isEmpty())
            {
                patient.setGender(session.getGender());
            }
            serviceFactory.createHospitalUserService().save(patient);
        }
    }

    private void addUser(Session session)
    {
        HospitalUser hospitalUser = new HospitalUser();
        hospitalUser.setAddress(session.getAddress());
        hospitalUser.setBirthDate(session.getBirthDate());
        hospitalUser.setGender(session.getGender());
        hospitalUser.setName(session.getName());
        hospitalUser.setRole(session.getRole());
        serviceFactory.createHospitalUserService().save(hospitalUser);
    }

}
