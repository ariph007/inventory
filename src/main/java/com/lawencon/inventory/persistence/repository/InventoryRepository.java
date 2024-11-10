package com.lawencon.inventory.persistence.repository;

import com.lawencon.inventory.persistence.entity.Inventory;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
  boolean existsByItemId(Long id);

  @Query(value = """
            SELECT
          COALESCE(SUM(CASE
              WHEN type = 't' THEN quantity
              WHEN type = 'w' THEN -quantity
              ELSE 0
          END), 0) AS current_stock
      FROM
          inventory
      WHERE
          item_id = :itemId
            """, nativeQuery = true)
  Integer getStockByItemId(@Param(value = "itemId") Long id);

}

