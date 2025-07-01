package hr.spring.web.trisek.service;

import hr.spring.web.trisek.dto.ItemDTO;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    List<ItemDTO> getAll();
    ItemDTO save(ItemDTO itemDTO);
    Optional<ItemDTO> getById(long id);
    boolean delete(long id);
}
