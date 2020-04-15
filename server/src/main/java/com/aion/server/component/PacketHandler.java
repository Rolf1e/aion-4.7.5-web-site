package com.aion.server.component;

import com.aion.server.network.dto.ServerAionConfig;
import com.aion.server.network.packets.client.AionPacket;
import org.springframework.stereotype.Component;

@Component
public class PacketHandler {

    private ServerAionConfig serverAionConfig;

    public void sendPacketToServer(AionPacket aionPacket) {

    }

}
