package com.lawencon.inventory.repository;

import com.lawencon.inventory.persistence.entity.Item;
import com.lawencon.inventory.persistence.repository.ItemRepository;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
public class ItemRepositoryUnitTest {
  @Autowired
  private ItemRepository itemRepository;

  @Test
  @DisplayName("Test 1: Save Item Test")
  @Order(1)
  @Rollback(value = false)
  public void saveItemTest() {
    // Action
    Item item = new Item();
    item.setName("Chair");
    item.setPrice(10.0);
    Item saveItem = itemRepository.saveAndFlush(item);

    //Verify
    System.out.println(item);
    Assertions.assertThat(saveItem.getId()).isGreaterThan(0);
  }

  @Test
  @DisplayName("Test 2: Get item by id")
  @Order(2)
  public void getItemByIdTest() {
    // Action
    Item item = itemRepository.findById(1L).orElseThrow(() -> new RuntimeException("Item not found"));

    // Verify
    System.out.println(item);
    Assertions.assertThat(item).isNotNull();
    Assertions.assertThat(item.getName()).isEqualTo("Chair");
    Assertions.assertThat(item.getPrice()).isEqualTo(10.0);
  }

  @Test
  @DisplayName("Test 3: Get All items")
  @Order(3)
  public void getAllItemsTest() {
    // Action
    List<Item> items = itemRepository.findAll();

    // Verify
    System.out.println(items);
    Assertions.assertThat(items.size()).isGreaterThan(0);
  }

  @Test
  @DisplayName("Test 4: Update item")
  @Order(4)
  @Rollback(value = false)
  public void updateItemTest() {
    // Action
    Item item = itemRepository.findById(1L).orElseThrow(() -> new RuntimeException("Item not found"));
    item.setName("Table");
    item.setPrice(20.0);
    itemRepository.saveAndFlush(item);

    // Verify
    Item updatedItem = itemRepository.findById(1L).orElseThrow(() -> new RuntimeException("Item not found"));
    System.out.println(updatedItem);
    Assertions.assertThat(updatedItem.getName()).isEqualTo("Table");
    Assertions.assertThat(updatedItem.getPrice()).isEqualTo(20.0);
  }

  @Test
  @DisplayName("Test 5: Delete item")
  @Order(5)
  @Rollback(value = false)
  public void deleteItemTest() {
    // Action
    itemRepository.deleteById(1L);

    // Verify
    Optional<Item> deletedItem = itemRepository.findById(1L);
    Assertions.assertThat(deletedItem).isEmpty();
  }

}