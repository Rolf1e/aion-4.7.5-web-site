package com.aion.server.handler.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AionItem {

    private String idPlayer;
    private String token;
    private String idItem;
    private String countItem;
}
