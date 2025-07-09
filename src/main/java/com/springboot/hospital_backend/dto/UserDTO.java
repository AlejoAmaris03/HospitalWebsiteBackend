package com.springboot.hospital_backend.dto;

import com.springboot.hospital_backend.models.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserDTO {
    private int id;
    private String name;
    private String surname;
    private String email;
    private String address;
    private String username;
    private String token;
    private Role role;
}
