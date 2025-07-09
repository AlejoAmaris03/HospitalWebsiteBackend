package com.springboot.hospital_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PatientDTO {
    private int id;
    private String name;
    private String surname;
    private boolean hasInsurance;
}
