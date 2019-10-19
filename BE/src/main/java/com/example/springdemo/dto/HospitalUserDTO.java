package com.example.springdemo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HospitalUserDTO {
    private Integer id;
    private String name;
    private String address;
    private String gender;
    private String birthDate;
}
