package com.aion.server.service.infra.utils;

public enum Currency {
    EURO(1),
    SHARD(50);

    private double taux;

    Currency(double taux) {
        this.taux = taux;
    }

    public double getTaux() {
        return taux;
    }
}
