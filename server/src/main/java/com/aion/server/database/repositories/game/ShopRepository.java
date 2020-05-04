package com.aion.server.database.repositories.game;

import com.aion.server.database.entity.game.Shop;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShopRepository extends CrudRepository<Shop, Long> {

    List<Shop> findAllByItemCategory(final String category);

    Optional<Shop> findByItemId(final long itemId);

    @Query(value = "SELECT DISTINCT itemCategory FROM Shop")
    List<String> getItemCategory();
}
