package org.example.dto;

import lombok.Data;

@Data
public class CreateLoginDTO {

    private String user;

    private String password;

    private Integer cart;

    private Boolean admin = false;
}
