package org.example.service;

import org.example.domain.entity.Cart;
import org.example.domain.entity.Client;
import org.example.domain.entity.Login;
import org.example.domain.entity.PurchaseItems;
import org.example.domain.repository.ClientRepository;
import org.example.domain.repository.PurchaseItemsRepository;
import org.example.event.DeleteCartEvent;
import org.example.event.DeleteLoginEvent;
import org.example.exception.ClientWithOpenedOrdersException;
import org.example.exception.ExistentUserException;
import org.example.exception.nonExistentIdException;
import org.hibernate.LazyInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PurchaseItemsRepository purchaseItemsRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Client findById(Integer id) {
        Optional<Client> client = clientRepository.findById(id);

        if (client.isPresent()){
            return client.get();
        }

        throw new nonExistentIdException("The client searched does not exists.");
    }


    public Client create(Login login, String name, String address){

        if (!clientRepository.existsByLogin(login)){

            try{

                Client client = new Client(login, name, address);

                return clientRepository.save(client);

            }catch (Exception e){
                throw new RuntimeException("nao sei");
            }

        }
        throw new ExistentUserException("This username is already in use.");
    }

    public Client update(String name, String address, Integer id) {

        Client client = findById(id);

        if (!Objects.equals(name, "")){
            client.setName(name);
        }

        if (!Objects.equals(address, "")){
            client.setAddress(address);
        }

        return clientRepository.save(client);

    }

    public void delete(Integer id) {

        try{
            Client client = findById(id);

            Login login = client.getLogin();

            Cart cart = login.getCart();

            List<PurchaseItems> purchaseItems = cart.getPurchaseItemsList();

            if (!orderService.hasOpenedOrders(login)){

                clientRepository.deleteById(id);

                applicationEventPublisher.publishEvent(new DeleteLoginEvent(
                        this, login));

                applicationEventPublisher.publishEvent(new DeleteCartEvent(this, cart));

                //purchaseItemsRepository.deleteAll(purchaseItems);
            }else {

                throw new ClientWithOpenedOrdersException("The client " +
                        "who you tried to delete has opened orders");

            }

        }catch (LazyInitializationException ignored){

        }


    }
}
