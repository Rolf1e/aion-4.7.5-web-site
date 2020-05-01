package com.aion.server.database.entity.game;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "players")
@Data
@NoArgsConstructor
/**
 * Players are Aion characters (No real dude)
 */
public class Player {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "account_id")
    private long accountId;

    @Column(name = "account_name")
    private String accountName;

}
