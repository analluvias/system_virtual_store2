package org.example.domain.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn(name = "login_id")
    private Login login;

    @Column
    private String address;

    public Client(Login login, String name, String address) {
        this.login = login;
        this.name = name;
        this.address = address;

    }
}