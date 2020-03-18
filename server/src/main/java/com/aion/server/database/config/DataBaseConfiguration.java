package com.aion.server.database.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DataBaseConfiguration {

    private String dataBaseDriver;
    private String dataBaseType;

}
