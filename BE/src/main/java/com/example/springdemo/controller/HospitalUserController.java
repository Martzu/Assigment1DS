package com.example.springdemo.controller;


import com.example.springdemo.dto.HospitalUserDTO;
import com.example.springdemo.entities.HospitalUser;
import com.example.springdemo.entities.HospitalUserRoles;
import com.example.springdemo.entities.Session;
import com.example.springdemo.services.ServiceFactory;
import lombok.RequiredArgsConstructor;
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
        Optional<HospitalUser> doctor = serviceFactory.createHospitalUserService().findById(session.getSessionId());
        if(doctor.isPresent() && doctor.get().getRole().equals(HospitalUserRoles.MEDIC))
        {
            HospitalUser patient = new HospitalUser();
            patient.setAddress(session.getAddress());
            patient.setBirthDate(session.getBirthDate());
            patient.setGender(session.getGender());
            patient.setName(session.getName());
            patient.setRole(HospitalUserRoles.PATIENT);
            serviceFactory.createHospitalUserService().save(patient);
        }
    }

    @DeleteMapping("/patient")
    public void deletePatient(@RequestBody Session session)
    {
        Optional<HospitalUser> doctor = serviceFactory.createHospitalUserService().findById(session.getSessionId());
        if(doctor.isPresent() && doctor.get().getRole().equals(HospitalUserRoles.MEDIC))
        {
            if (serviceFactory.createHospitalUserService().findById(session.getPatientIdToDelete()).isPresent())
            {
                //serviceFactory.createHospitalUserService().removeHospitalUserById(session.getPatientIdToDelete());
                serviceFactory.createHospitalUserService().removeHospitalUser(serviceFactory.createHospitalUserService().findById(session.getPatientIdToDelete()).get());
            }
        }
    }


    @PostMapping("/patients")
    public List<HospitalUserDTO> getPatientsOfDoctor(@RequestBody Session session)
    {
        Optional<HospitalUser> doctor = serviceFactory.createHospitalUserService().findById(session.getSessionId());
        List<HospitalUserDTO> patients = new ArrayList<>();
        if(doctor.isPresent() && doctor.get().getRole().equals(HospitalUserRoles.MEDIC)) {
            patients = serviceFactory.createHospitalUserService().getAllByRole(HospitalUserRoles.PATIENT).stream().map(patient -> new HospitalUserDTO(patient.getId(), patient.getName(), patient.getAddress(), patient.getGender(), patient.getBirthDate())).collect(Collectors.toList());
        }
        return patients;
    }

    @PostMapping("/caretakers")
    //TODO make it post with sessionId
    public List<HospitalUserDTO> getCaretakers(@RequestBody Session session)
    {
        List<HospitalUserDTO> caretakers = new ArrayList<>();
        if(serviceFactory.createHospitalUserService().findById(session.getSessionId()).isPresent() && serviceFactory.createHospitalUserService().findById(session.getSessionId()).get().getRole().equals(HospitalUserRoles.MEDIC))
        {
            caretakers = serviceFactory.createHospitalUserService().getAllByRole(HospitalUserRoles.CARETAKER).stream().map(patient -> new HospitalUserDTO(patient.getId(), patient.getName(), patient.getAddress(), patient.getGender(), patient.getBirthDate())).collect(Collectors.toList());
        }
        return caretakers;
    }



}
