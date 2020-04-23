package com.aion.server.database.repositories.game;

import com.aion.server.database.entity.Shop;
import org.springframework.data.repository.CrudRepository;

public interface ShopRepository extends CrudRepository<Shop, Long> {

    Shop findAllByItemCategory(final String category);
}
