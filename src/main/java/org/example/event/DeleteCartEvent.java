package org.example.event;

import lombok.Data;
import org.example.domain.entity.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;

@Data
public class DeleteCartEvent extends ApplicationEvent{

    @Autowired
    Cart cart;


    public DeleteCartEvent(Object source) {
        super(source);
    }

    public DeleteCartEvent(Object source, Cart cart) {
        super(source);
        this.cart = cart;
    }
}
