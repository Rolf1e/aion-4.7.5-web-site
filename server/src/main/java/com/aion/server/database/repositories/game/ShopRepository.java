package com.aion.server.database.repositories.game;

import com.aion.server.database.entity.game.Shop;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopRepository extends CrudRepository<Shop, Long> {

    List<Shop> findAllByItemCategory(final String category);

    Shop findByItemId(final int itemId);
}
