package com.lawencon.inventory.persistence.repository;

import com.lawencon.inventory.persistence.entity.Order;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

  @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
  Optional<Order> findTopByOrderByOrderNoDesc();
}
