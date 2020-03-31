package com.aion.server.handler.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InputUserInfos {

    private String username;
    private String password;
    private String token;

    public InputUserInfos(String username,
                          String password) {

        this.username = username;
        this.password = password;
    }
}
