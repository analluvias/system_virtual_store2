package org.example.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "login")
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    @Column(name = "id")
    private Integer id;

    @Column(name = "user", unique = true)
    private String user;

    @Column(name = "password")
    private String password;

    @OneToOne
    @JoinColumn(name = "cart", unique = true)
    private Cart cart;

    @Column
    private boolean admin;

//    @OneToMany
//    @JoinColumn(name="login_id")
//    private List<Order> orders = new ArrayList<>();

    public Login(String user, String password, Cart cart, Boolean admin) {
        this.cart = cart;
        this.user = user;
        this.password = password;
        this.admin = admin;
    }

    public Login(String user, String password) {
        this.user = user;
        this.password = password;
    }
}