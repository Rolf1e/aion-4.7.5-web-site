package com.aion.server.component.shop.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AionLetter {

    private final String receiver;
    private final String sender;
    private final String content;
    private LetterAttachment attachment;

}
