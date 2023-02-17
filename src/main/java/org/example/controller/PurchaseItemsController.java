package org.example.controller;

import org.example.domain.entity.Product;
import org.example.domain.entity.PurchaseItems;
import org.example.dto.PurchaseItemsDTO;
import org.example.service.ProductService;
import org.example.service.PurchaseItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchaseitems")
public class PurchaseItemsController {

    @Autowired
    private PurchaseItemsService purchaseItemsService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<PurchaseItems> findAll (){
        return purchaseItemsService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseItems> findById (@PathVariable("id") Integer id){

        PurchaseItems purchaseItem = purchaseItemsService.findById(id);

        return new ResponseEntity<>(
                purchaseItem,
                HttpStatus.OK
        );

    }

    @PostMapping
    public ResponseEntity<PurchaseItems> create(@RequestBody PurchaseItemsDTO purchaseItemsDTO){

        Product product = productService.findById(purchaseItemsDTO.getProduct());

        return new ResponseEntity<>(
                purchaseItemsService.create(product, purchaseItemsDTO.getQuantity()),
                HttpStatus.CREATED
        );


    }




}
