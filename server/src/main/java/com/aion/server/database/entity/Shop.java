package com.aion.server.database.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "shop")
@NoArgsConstructor
@AllArgsConstructor
public class Shop {

    @Id
    @Column(name = "object_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public String objectId;

    @Column
    public String itemId;

    @Column
    public String itemName;

    @Column
    public String itemCategory;

    @Column
    public String itemDescription;

    @Column
    public String itemCount;

    @Column
    public String itemPrice;

    @Column
    public String itemPathToImageColumn;

}
