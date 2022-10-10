package org.example.dto;

import lombok.Data;
import org.example.domain.entity.Login;

@Data
public class ClientDTO {

    private String name;

    private Integer login;

    private String address;

}
