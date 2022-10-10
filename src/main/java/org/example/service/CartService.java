package org.example.service;

import org.example.domain.entity.Cart;
import org.example.domain.entity.PurchaseItems;
import org.example.domain.repository.CartRepository;
import org.example.exception.IdHasDependenceException;
import org.example.exception.nonExistentIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public List<Cart> findAll() {

        return cartRepository.findAll();

    }

    public Cart findById(Integer id) {

        Optional<Cart> cart = cartRepository.findById(id);

        if (cart.isPresent()){
            return cart.get();
        }

        throw new nonExistentIdException("The cart searched does not exists.");
    }

    public Cart create(List<PurchaseItems> purchases) {

        Cart cart = new Cart(purchases);

        return cartRepository.save(cart);
    }

    //como nós só temos um carro, o que vamos fazer é apagar todas as suas compras,
    //mas não deletamos o carro efetivamente.
    public void changeStatus(Cart cart) {
        Cart cartById = findById(cart.getId());

        List<PurchaseItems> emptyList = new ArrayList<>();

        cartById.setPurchaseItemsList(emptyList);

        cartRepository.save(cartById);
    }

    public Cart update(List<PurchaseItems> purchases, Integer id) {

        Cart cartById = findById(id);
        cartById.setPurchaseItemsList(purchases);
        System.out.println(cartById.toString());
        return cartRepository.save(cartById);
    }

    public void delete(Cart cart) {
        Cart cartById = findById(cart.getId());

        cartRepository.deleteById(cart.getId());
    }

    public void deleteById(Integer id) {
        Cart cart = findById(id);

        try {
            cartRepository.delete(cart);
        }catch (Exception e){
            throw new IdHasDependenceException("The Cart you tried to delete is part of a login.");
        }
    }
}
