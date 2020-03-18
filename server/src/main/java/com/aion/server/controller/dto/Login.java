package com.aion.server.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Login {

    private String username;
    private String password;
}
