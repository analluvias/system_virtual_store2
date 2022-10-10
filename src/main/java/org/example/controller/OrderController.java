package org.example.controller;

import org.example.domain.entity.*;
import org.example.domain.enums.Status;
import org.example.dto.OrderDTO;
import org.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


//esse endpoint so permite a mudança do status de pedido de aberto para fechado
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    //método não existe, na verdade, iria enviar os dados para um serviço externo
    // para gerar um boleto, por exemplo
    @GetMapping("/generatepayment/{id}")
    public ResponseEntity<Payment> generatePayment(@PathVariable("id") Integer id){

        Order order = orderService.findById(id);

        Payment payment = new Payment();

        return new ResponseEntity<Payment>(
                payment,
                HttpStatus.OK
        );

    }

    @PatchMapping("/{id}")
    public ResponseEntity<Order> closeOrder(@PathVariable("id") Integer id, @RequestBody OrderDTO orderDTO){

        Status status = orderDTO.getStatus();

        Order order = orderService.changeStatusById(id, status);

        return new ResponseEntity<Order>(
                order,
                HttpStatus.NO_CONTENT
        );

    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> findById (@PathVariable("id") Integer id){

        Order order = orderService.findById(id);

        return new ResponseEntity<Order>(
                order,
                HttpStatus.OK
        );

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {

        orderService.delete(id);
    }

}
