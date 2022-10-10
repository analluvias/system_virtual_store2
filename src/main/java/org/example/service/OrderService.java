package org.example.service;

import org.example.domain.entity.Login;
import org.example.domain.entity.Order;
import org.example.domain.entity.PurchaseItems;
import org.example.domain.enums.Status;
import org.example.domain.repository.OrderRepository;
import org.example.exception.IdHasDependenceException;
import org.example.exception.nonExistentIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order findById(Integer id) {

        Optional<Order> order = orderRepository.findById(id);

        if (order.isPresent()){
            return order.get();
        }

        throw new nonExistentIdException("The order searched does not exist.");

    }

    public Order create(List<PurchaseItems> purchases, Login login) {

        Order order = new Order(purchases, login);

        return orderRepository.save(order);

    }

    //nesse caso só vamos mudar o seu status
    public Order changeStatusById(Integer id, Status status){
        Order order = findById(id);

        order.setStatus(status);

        return orderRepository.save(order);
    }

    public boolean existsByLogin(Login login) {
        return orderRepository.existsByLogin(login);
    }

    public boolean hasOpenedOrders(Login login){
        if (existsByLogin(login)){

            List<Order> orders = orderRepository.findByLogin(login);

            for (Order order: orders){
                if (order.getStatus() == Status.OPENED){
                    return true;
                }
            }

            return false;


        }
        return false;

    }

    public void delete(Integer id) {
        Order order = findById(id);

        try {
            orderRepository.delete(order);
        }catch (Exception e){
            throw new IdHasDependenceException("The order that you tried to delete is part of a Cart.");
        }

    }
}
