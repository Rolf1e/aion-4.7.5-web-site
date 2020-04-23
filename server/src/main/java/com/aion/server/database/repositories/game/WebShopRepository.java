package com.aion.server.database.repositories.game;

import com.aion.server.database.entity.WebShop;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface WebShopRepository extends CrudRepository<WebShop, Long> {

}
