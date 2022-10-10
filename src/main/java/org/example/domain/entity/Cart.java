package org.example.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.JoinColumn;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    @Column(name = "id")
    private Integer id;

    @ManyToMany
    @JoinTable(name = "cart_purchaseitems",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "purchase_items_id"))
    private List<PurchaseItems> purchaseItemsList = new ArrayList<>();

    public Cart(List<PurchaseItems> purchaseItems){
        this.purchaseItemsList = purchaseItems;
    }

//    @Column
//    private Order order;
}
