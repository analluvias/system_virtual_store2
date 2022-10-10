package org.example.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.dto.ProductEspecificationDTO;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "product_especification")
public class ProductEspecification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    @Column(name = "id")
    private Integer id;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    public ProductEspecification(ProductEspecificationDTO productEspecificationDTO){
        this.description = productEspecificationDTO.getDescription();
        this.price = productEspecificationDTO.getPrice();
    }
}
