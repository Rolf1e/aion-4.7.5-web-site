package com.aion.server.component.mail.infra.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebMailData {

    private String token;
    private List<String> to;
    private String subject;
    private String content;

}
