package com.aion.server.network.infra;

import com.aion.server.network.dto.ServerAionConfig;
import com.aion.server.network.packets.PacketWriter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.nio.channels.SocketChannel;


@RequiredArgsConstructor
public class AionServerConnection {

    @Getter
    private final SocketChannel socketChannel;
    private final ServerAionConfig serverAionConfig;
    private final PacketWriter writer;

}
