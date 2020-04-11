package com.aion.server.handler.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InputUserInfos {

    private String id;
    private String username;
    private String password;
    private String token;

    public InputUserInfos(String username,
                          String password) {

        this.username = username;
        this.password = password;
    }

    public InputUserInfos(OutputUserInfos outputUserInfos) {
        this.id = outputUserInfos.getId();
        this.username = outputUserInfos.getUsername();
        this.password = outputUserInfos.getPassword();
        this.token = outputUserInfos.getToken();
    }
}
