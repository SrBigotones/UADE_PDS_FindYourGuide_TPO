package com.uade.pds.findyourguide.controller.dto;

import lombok.Data;

@Data
public class UserDTO {
    private long id;
    private String name;
    private String surname;
    private String sex;
    private String dni;
    private String email;
    private String phoneNumber;
    private String perfilImg;
    private String password;
}
