package com.usersservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_user;
    private String name;
    private String lastname;
    private String email;
    private String password;
    @ElementCollection
    @CollectionTable(name = "user_favorites", joinColumns = @JoinColumn(name = "id_user"))
    @Column(name = "id_product")
    private List<Long> favorite_product_ids;
    @ElementCollection
    @CollectionTable(name = "user_payments", joinColumns = @JoinColumn(name = "id_user"))
    @Column(name = "payment_id")
    private List<Long> payments_ids;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id_user")
    , inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id_role"))
    private List<Role> rol = new ArrayList<>();
    private Long id_cart;
}
