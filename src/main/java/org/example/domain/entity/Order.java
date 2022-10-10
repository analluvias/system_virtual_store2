package org.example.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.domain.enums.Status;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "order_")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    @Column(name = "id")
    private Integer id;

    @Column(name = "total_price", precision = 8, scale = 2)
    private BigDecimal total_price;

    @Column(name = "purchase_items")
    @ManyToMany
    @JoinTable(name = "order_purchaseitems",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "purchase_items_id"))
    private List<PurchaseItems> purchaseItemsList = new ArrayList<>();

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status = Status.OPENED;

    @ManyToOne
    @JoinColumn(name="login_id")
    private Login login;

    @Transient
    private Payment payment;

    public Order(List<PurchaseItems> purchases, Login login) {

        this.purchaseItemsList = purchases;

        double v = 0;
        for (PurchaseItems p:purchaseItemsList) {
            v += p.getProduct().getProductEspecification().getPrice() * p.getQuantity();
        }
        this.total_price = BigDecimal.valueOf(v);
        
        this.login = login;

    }
}
