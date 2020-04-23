package com.aion.server.database.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "account_data")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AccountData {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "activated")
    private int activated;

    @Column(name = "token")
    private String token;

    @Column(name = "membership")
    private int membership;

    @Column(name = "email")
    private String email;

    @Column(name = "toll")
    private String toll;

}
