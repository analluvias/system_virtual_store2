package org.example.dto;

import lombok.Data;

import javax.persistence.Column;

@Data
public class ProductEspecificationDTO {

    private String description;

    private Double price;
}
