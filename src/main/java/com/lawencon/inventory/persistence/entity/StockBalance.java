package com.lawencon.inventory.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


@Entity
@Table(name = "stock_balance")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE stock_balance SET deleted_at = now() WHERE id=? AND version =?")
@Where(clause = "deleted_at IS NULL")
public class StockBalance extends AuditableEntity{
    @OneToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(name = "topup", nullable = false)
    private Integer topup;

    @Column(name = "withdraw", nullable = false)
    private Integer withdraw;

    @Column(name = "current_balance", nullable = false)
    private Integer currentBalance;
}
