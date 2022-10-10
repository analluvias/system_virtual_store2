package org.example.event.listeners;

import org.example.domain.entity.Cart;
import org.example.domain.entity.Login;
import org.example.domain.entity.PurchaseItems;
import org.example.event.CloseStatusCartEvent;
import org.example.service.LoginService;
import org.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GenerateOrderListener {

    @Autowired
    private OrderService orderService;

    @Autowired
    private LoginService loginService;

    @Async
    @EventListener
    void GenerateOrder (CloseStatusCartEvent closeStatusCartEvent){
        Cart cart = closeStatusCartEvent.getCart();
        Login login = loginService.findLoginByCart(cart);


        List<PurchaseItems> purchaseItems = closeStatusCartEvent.getPurchaseItems();
        Integer id = closeStatusCartEvent.getId();

        orderService.create(purchaseItems, login);
    }
}
