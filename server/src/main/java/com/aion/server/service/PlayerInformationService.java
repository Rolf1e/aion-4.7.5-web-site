package com.aion.server.service;

import com.aion.server.database.entity.game.Player;
import com.aion.server.database.repositories.game.PlayerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PlayerInformationService {

    private final PlayerRepository playerRepository;

    public List<Player> getWholeInfosForPlayer(final long id) {
        return playerRepository.findAllByAccountId(id)
                .orElse(Collections.emptyList());
    }

    public boolean checkPlayerExist(final String name) {
        return playerRepository.existsByName(name);
    }
}
