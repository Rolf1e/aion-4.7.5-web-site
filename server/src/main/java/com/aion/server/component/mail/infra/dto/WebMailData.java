package com.aion.server.component.mail.infra.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class WebMailData {

    private List<String> to;
    private String subject;
    private String content;

}
