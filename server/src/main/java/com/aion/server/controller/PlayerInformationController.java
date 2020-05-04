package com.aion.server.controller;

import com.aion.server.database.entity.game.Player;
import com.aion.server.service.PlayerInformationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
public class PlayerInformationController {

    private final PlayerInformationService playerInformationService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/list-players")
    public List<Player> getListPlayers(@RequestParam(value = "idUser") final int idUser) {
        log.info("Get player informations for user {}", idUser);
        return playerInformationService.getWholeInfosForPlayer(idUser);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/check-players-exist")
    public boolean checkPlayersExist(@RequestParam(value = "name") final String name) {
        log.info("Check if player {} exist", name);
        return playerInformationService.checkPlayerExist(name);
    }
}
