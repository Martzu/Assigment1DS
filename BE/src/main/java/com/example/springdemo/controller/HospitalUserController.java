package com.example.springdemo.controller;


import com.example.springdemo.entities.HospitalUser;
import com.example.springdemo.services.HospitalUserService;
import com.example.springdemo.services.ServiceFactory;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@CrossOrigin
@RestController
@RequiredArgsConstructor
public class HospitalUserController {


    private Map<String, Boolean> connectedSessions = new ConcurrentHashMap<>();
    private final ServiceFactory serviceFactory;

    @PostMapping("/login")
    public Object authenticate(@RequestBody String credentials)
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
        //Object jsonUser = new Gson().toJson(user);
        return user.toString();
    }



    /*@CrossOrigin
    @PostMapping("/getPatients")
    public List<Object> getPatientsOfDoctor(@RequestBody String sessionId)
    {

    }*/


}
