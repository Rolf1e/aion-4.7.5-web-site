package com.aion.server.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InputUserInfos {

    private String username;
    private String password;
    private String token;
}
