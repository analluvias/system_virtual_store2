package org.example.controller;

import org.example.domain.entity.Cart;
import org.example.domain.entity.PurchaseItems;
import org.example.domain.enums.Status;
import org.example.dto.PostCartDTO;
import org.example.dto.PurchasesCartDTO;
import org.example.dto.StatusCartDTO;
import org.example.event.CloseStatusCartEvent;
import org.example.service.CartService;
//import org.example.service.OrderService;
import org.example.service.PurchaseItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private PurchaseItemsService purchaseItemsService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

/*    @Autowired
    private OrderService orderService; */

    @GetMapping
    private List<Cart> findAll (){
        return cartService.findAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Cart> findById (@PathVariable("id") Integer id){

        Cart cart = cartService.findById(id);

        return new ResponseEntity<Cart>(
                cart,
                HttpStatus.OK
        );

    }

    @PostMapping
    public ResponseEntity<Cart> create(@RequestBody PostCartDTO cartDTO){

        List<Integer> purchasesId = cartDTO.getPurchases();

        List<PurchaseItems> purchases = purchaseItemsService.findAllById(purchasesId);

        Cart cart = cartService.create(purchases);

        return new ResponseEntity<Cart>(
                cart,
                HttpStatus.CREATED
        );

    }

    @PatchMapping("/status/{id}")
    public ResponseEntity updateStatus(@PathVariable("id") Integer id, @RequestBody StatusCartDTO statusCartDTO){

        Cart cart = cartService.findById(id);

        List<PurchaseItems> purchases = new ArrayList<>(cart.getPurchaseItemsList());

        if (Status.CLOSED.toString().equals(statusCartDTO.getStatus().toString())){


            applicationEventPublisher.publishEvent(new CloseStatusCartEvent(this, purchases, id, cart));

            cartService.changeStatus(cart);

            return new ResponseEntity(HttpStatus.NO_CONTENT);

        }

        return new ResponseEntity(
                cart,
                HttpStatus.OK
        );

    }

    @PatchMapping("/purchases/{id}")
    public ResponseEntity<Cart> update(@PathVariable("id") Integer id, @RequestBody PurchasesCartDTO purchasesCartDTO){

        List<Integer> purchasesId = purchasesCartDTO.getPurchases();

        System.out.println(purchasesId);

        List<PurchaseItems> purchases = purchaseItemsService.findAllById(purchasesId);

        Cart cart = cartService.update(purchases, id);

        return new ResponseEntity<>(
                cart,
                HttpStatus.NO_CONTENT
        );

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {

        cartService.deleteById(id);
    }



}
