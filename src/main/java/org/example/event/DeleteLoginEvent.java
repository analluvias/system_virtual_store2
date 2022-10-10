package org.example.event;

import lombok.Data;
import org.example.domain.entity.Cart;
import org.example.domain.entity.Client;
import org.example.domain.entity.Login;
import org.example.domain.entity.PurchaseItems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Data
public class DeleteLoginEvent extends ApplicationEvent {

    @Autowired
    Login login;

    @Autowired
    List<PurchaseItems> purchaseItems;


    public DeleteLoginEvent(Object source) {
        super(source);
    }

    public DeleteLoginEvent(Object source, Login login) {
        super(source);
        this.login = login;
    }


}
