package com.aion.server.database.entity.login;

import com.aion.server.service.infra.dto.InputUserInfos;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "account_data", schema = "ac47_server_ls")
@NoArgsConstructor
@AllArgsConstructor
@Getter
/**
 * Represent an account (real dude is behind this)
 */
public class AccountData {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Setter
    @Column(name = "activated")
    private int activated;

    @Column(name = "token")
    private String token;

    @Column(name = "membership")
    private int membership;

    @Column(name = "email")
    private String email;

    @Column(name = "toll")
    private long toll;

    @Column(name = "created_at", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public AccountData(final InputUserInfos userInfos,
                       final String token,
                       final String encryptedPassword) {

        this.name = userInfos.getUsername();
        this.password = encryptedPassword;
        this.email = userInfos.getMail();
        this.token = token;
    }

    public AccountData(final String name,
                       final String password,
                       final String token,
                       final Date updatedAt) {

        this.name = name;
        this.password = password;
        this.token = token;
        this.updatedAt = updatedAt;
    }

    public long giveToll(final long toGive) {
        this.toll += toGive;
        return this.toll;
    }

    public long takeToll(final long toTake) {
        this.toll -= toTake;
        return this.toll;
    }
}
