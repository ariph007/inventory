package com.lawencon.inventory.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


@Entity
@Table(name = "item", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "deleted_at"})})
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE item SET deleted_at = now() WHERE id=? AND version =?")
@Where(clause = "deleted_at IS NULL")
public class Item extends AuditableEntity{
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Double price;
}
