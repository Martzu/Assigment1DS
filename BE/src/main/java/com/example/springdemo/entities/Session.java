package com.example.springdemo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Session {
    private Integer sessionId;
    private Integer patientIdToDelete;
    private String role;
    private String name;
    private String address;
    private String birthDate;
    private String gender;

    public Session(Integer id, String role)
    {
        this.sessionId = id;
        this.role = role;
    }
}
