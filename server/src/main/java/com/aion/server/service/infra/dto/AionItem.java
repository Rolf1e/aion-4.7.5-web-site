package com.aion.server.service.infra.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AionItem {

    private long idPlayer;
    private String token;
    private long idItem;
    private int countItem;
    private String recipient;
}
