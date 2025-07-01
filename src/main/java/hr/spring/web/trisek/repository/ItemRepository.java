package hr.spring.web.trisek.repository;

import hr.spring.web.trisek.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {
    List<Item> getAll();
    Item save(Item item);
    boolean delete(Integer id);
    Optional<Item> getById(Integer id);
}
