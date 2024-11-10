package com.lawencon.inventory.persistence.repository;

import com.lawencon.inventory.persistence.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
}
