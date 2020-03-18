package com.aion.server.database.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Authentication {

    private String user;
    private String password;
    private String host;
    private String port;
}
