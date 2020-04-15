package com.aion.server.network.packets;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.nio.ByteBuffer;

@RequiredArgsConstructor
public class PacketWriter {

    @Getter
    private final ByteBuffer byteBuffer;

    public final void writeString(String text) {
        if (text != null) {
            for (char car : text.toCharArray()) {
                byteBuffer.putChar(car);
            }
        }
        byteBuffer.putChar('\u0000');
    }

    public final void writeInt(int value) {
        byteBuffer.putInt(value);
    }

    public final void writeDouble(long value) {
        byteBuffer.putLong(value);
    }

    public final void writeShort(short value) {
        byteBuffer.putShort(value);
    }

    public final void writeDouble(double value) {
        byteBuffer.putDouble(value);
    }

    public final void writeByte(byte value) {
        byteBuffer.put(value);
    }

    public final void writeBytes(byte[] value) {
        byteBuffer.put(value);
    }

}
