package com.aion.server.network.packets.client;

public class CM_Send_Mail extends BaseAionPacket{

    private String recipientName;
    private String title;
    private String message;
    private int itemObjId;
    private int itemCount;
    private int kinahCount;
    private int idLetterType;

    public CM_Send_Mail(int opCode) {
        super(opCode);
    }
}
