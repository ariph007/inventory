package com.lawencon.inventory.persistence.repository;

import com.lawencon.inventory.persistence.entity.StockBalance;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

@Repository
public interface StockBalanceRepository extends JpaRepository<StockBalance, Long> {

  @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
  Optional<StockBalance> findByItemId(Long Long);
}
