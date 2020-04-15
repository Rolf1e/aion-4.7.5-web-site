package com.aion.server.network.packets.client;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class BaseAionPacket implements AionPacket {

    private final int opCode;

    @Override
    public int getOpCode() {
        return opCode;
    }
}
