package org.example.dto;

import lombok.Data;
import org.example.domain.enums.Status;

@Data
public class OrderDTO {

    private Status status;
}
