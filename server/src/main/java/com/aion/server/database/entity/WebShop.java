package com.aion.server.database.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "webshop")
@NoArgsConstructor
@AllArgsConstructor
public class WebShop {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "recipient")
    private String recipient;

    @Column(name = "item_desc")
    private String itemDesc;

    @Column(name = "item_id")
    private String itemId;

    @Column(name = "count")
    private int count;

    @Column(name = "toll")
    private int toll;
}
