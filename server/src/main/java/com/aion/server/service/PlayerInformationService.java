package com.aion.server.service;

import com.aion.server.database.entity.game.Player;
import com.aion.server.database.repositories.game.PlayerRepository;
import com.aion.server.service.infra.dto.Factions;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static com.aion.server.service.infra.utils.PlayerRace.ASMOS;
import static com.aion.server.service.infra.utils.PlayerRace.ELYOS;

@Slf4j
@Service
@AllArgsConstructor
public class PlayerInformationService {

    private final PlayerRepository playerRepository;

    public List<Player> getWholeInfosForPlayer(final long id) {
        return playerRepository.findAllByAccountId(id)
                .orElse(Collections.emptyList());
    }

    public Factions getFactionsStats() {
        final long elyos = playerRepository.countByRaceAndOnline(ELYOS.getRace(), 1);
        final long asmos = playerRepository.countByRaceAndOnline(ASMOS.getRace(), 1);
        return new Factions(asmos, elyos);
    }

    public boolean checkPlayerExist(final String name) {
        return playerRepository.existsByName(name);
    }
}
