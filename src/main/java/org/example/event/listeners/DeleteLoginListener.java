package org.example.event.listeners;

import org.example.domain.entity.Login;
import org.example.event.DeleteLoginEvent;
import org.example.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class DeleteLoginListener {

    @Autowired
    private LoginService loginService;

    @Async
    @EventListener()
    void deleteLogin (DeleteLoginEvent deleteLoginEvent){
        Login login = deleteLoginEvent.getLogin();

        loginService.delete(login);

    }
}
