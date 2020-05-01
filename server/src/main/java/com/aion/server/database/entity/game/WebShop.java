package com.aion.server.database.entity.game;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "webshop", schema = "ac47_server_gs")
@Data
@NoArgsConstructor
public class WebShop {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "recipient")
    private String recipient;

    @Column(name = "item_desc")
    private String itemDesc;

    @Column(name = "item_id")
    private int itemId;

    @Column(name = "count")
    private int count;

    @Column(name = "toll")
    private int toll;

    public WebShop(final String recipient,
                   final String itemDesc,
                   final int itemId,
                   final int count,
                   final int toll) {

        this.recipient = recipient;
        this.itemDesc = itemDesc;
        this.itemId = itemId;
        this.count = count;
        this.toll = toll;
    }
}
