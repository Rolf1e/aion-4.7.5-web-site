package com.aion.server.database.repositories.game;

import com.aion.server.database.entity.game.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Long> {

    Optional<List<Player>> findAllByAccountId(final long accountId);

    boolean existsByName(final String name);
}
