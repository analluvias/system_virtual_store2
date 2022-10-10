package org.example.event;

import lombok.Data;
import org.example.controller.CartController;
import org.example.domain.entity.Cart;
import org.example.domain.entity.PurchaseItems;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Data
public class CloseStatusCartEvent extends ApplicationEvent {

    private List<PurchaseItems> purchaseItems;
    private Integer id;

    private Cart cart;

    public CloseStatusCartEvent(Object source) {
        super(source);
    }

    public CloseStatusCartEvent(Object source, List<PurchaseItems> purchases, Integer id, Cart cart) {
        super(source);
        this.purchaseItems = purchases;
        this.id = id;
        this.cart = cart;
    }
}
