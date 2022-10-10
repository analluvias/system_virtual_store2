package org.example.controller;

import org.example.domain.entity.Product;
import org.example.domain.entity.ProductEspecification;
import org.example.dto.ProductEspecificationDTO;
import org.example.service.ProductEspecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productespecifications")
public class ProductEspecificationController {

    @Autowired
    private ProductEspecificationService productEspecificationService;

    @GetMapping
    public List<ProductEspecification> findAll (){
        return productEspecificationService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductEspecification> findById (@PathVariable("id") Integer id){

        ProductEspecification productEspecification = productEspecificationService.findById(id);

        return new ResponseEntity<ProductEspecification>(
                productEspecification,
                HttpStatus.OK
        );

    }

    @PostMapping
    public ResponseEntity<ProductEspecification> create(@RequestBody ProductEspecificationDTO productEspecificationDTO){

        ProductEspecification productEspecification = new ProductEspecification(productEspecificationDTO);

        return new ResponseEntity<ProductEspecification>(
                productEspecificationService.create(productEspecification),
                HttpStatus.CREATED
        );

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {

        productEspecificationService.delete(id);
    }

}
