package org.example.controller;

import org.example.domain.entity.Product;
import org.example.domain.entity.ProductEspecification;
import org.example.dto.ProductDTO;
import org.example.dto.ProductEspecificationDTO;
import org.example.service.ProductEspecificationService;
import org.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class productcontroller {

    @Autowired
    private ProductService productService;

    @Autowired
    ProductEspecificationService productEspecificationService;

    @GetMapping
    public List<Product> findAll (){
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById (@PathVariable("id") Integer id){

        Product product = productService.findById(id);

        return new ResponseEntity<Product>(
                product,
                HttpStatus.OK
        );

    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody ProductDTO productDTO){

        Integer especificationid = productDTO.getEspecificationid();

        ProductEspecification productEspecification = productEspecificationService
                .findById(especificationid);

        return new ResponseEntity<Product>(
                productService.create(productEspecification),
                HttpStatus.CREATED
        );

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {

        productService.delete(id);
    }


}
