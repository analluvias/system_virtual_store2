package org.example.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
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

//    @OneToMany
//    @JoinColumn(name="login_id")
//    private List<Order> orders = new ArrayList<>();

    public Login(String user, String password, Cart cart) {
        this.cart = cart;
        this.user = user;
        this.password = password;
    }

    public Login(String user, String password) {
        this.user = user;
        this.password = password;
    }
}