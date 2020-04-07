package com.aion.server.network.packets;

import org.junit.Test;

import java.nio.ByteBuffer;

public class PacketWriterTest {

    @Test
    public void should_put_string() {
        //TODO
        PacketWriter packetWriter = new PacketWriter(ByteBuffer.allocate(20));
        packetWriter.writeString("Bonjour");
        final ByteBuffer byteBuffer = packetWriter.getByteBuffer();
//        byteBuffer.asCharBuffer();
        for(int i = 0; i < 20; i++) {
            System.out.print(byteBuffer.getChar(i) + "|");
        }
    }

}
