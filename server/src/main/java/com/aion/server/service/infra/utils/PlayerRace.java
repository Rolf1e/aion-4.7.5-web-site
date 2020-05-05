package com.aion.server.service.infra.utils;

import lombok.Getter;

public enum PlayerRace {

    ASMOS("ASMODIANS"),
    ELYOS("ELYOS");

    @Getter
    private String race;

    PlayerRace(String race) {
        this.race = race;
    }

}
