package com.aion.server.database.entity.game;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "shop", schema = "ac47_server_gs")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Shop {

    @Id
    @Column(name = "object_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String objectId;

    @Column(name = "item_id")
    private long itemId;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_category")
    private String itemCategory;

    @Column(name = "item_desc")
    private String itemDescription;

    @Column(name = "item_count")
    private int itemCount;

    @Column(name = "price")
    private int itemPrice;

    @Column(name = "item_image_path")
    private String itemPathToImageColumn;

}
