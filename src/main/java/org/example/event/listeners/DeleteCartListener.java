package org.example.event.listeners;

import org.example.domain.entity.Cart;
import org.example.event.DeleteCartEvent;
import org.example.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class DeleteCartListener {

    @Autowired
    private CartService cartService;

    @Async
    @EventListener()
    void deleteCart (DeleteCartEvent deleteCartEvent){

        Cart cart = deleteCartEvent.getCart();

        cartService.delete(cart);

    }

}
