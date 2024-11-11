package com.lawencon.inventory.persistence.repository;

import com.lawencon.inventory.persistence.entity.Item;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
  boolean existsByName(String name);
}
