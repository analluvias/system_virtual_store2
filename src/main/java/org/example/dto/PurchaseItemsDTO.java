package org.example.dto;

import lombok.Data;
import org.example.domain.entity.Product;

@Data
public class PurchaseItemsDTO {

    private Integer product;

    private Integer quantity;
}
