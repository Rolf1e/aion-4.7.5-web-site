package com.aion.server.service.infra.utils;

public class CurrencyConverter {

    private final Currency to;

    /**
     * EURO -> SHARD
     */
    public CurrencyConverter() {
        this.to = Currency.SHARD;
    }

    /**
     * EURO -> to
     *
     * @param to
     */
    public CurrencyConverter(final Currency to) {
        this.to = to;
    }

    public double convert(final double value) {
        return value * to.getTaux();
    }


}
