package org.example.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.domain.repository.ProductEspecificationRepository;
import org.example.dto.ProductDTO;
import org.example.exception.nonExistentIdException;
import org.example.service.ProductEspecificationService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "product_especification_id")
    private ProductEspecification productEspecification;

    public Product(ProductEspecification productEspecification){
        this.productEspecification = productEspecification;
    }

}
