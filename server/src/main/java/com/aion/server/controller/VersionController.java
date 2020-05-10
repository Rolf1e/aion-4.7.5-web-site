package com.aion.server.controller;

import com.aion.server.service.infra.dto.Version;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class VersionController {

    @CrossOrigin
    @GetMapping(value = "/version")
    public Version getVersion() {
        log.info("Get version infos");
        final Version version = new Version();
        version.setVersion("1.0");
        return version;
    }
}
