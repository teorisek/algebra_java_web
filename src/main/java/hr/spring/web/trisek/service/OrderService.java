package hr.spring.web.trisek.service;

import hr.spring.web.trisek.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<Order> getAll();
    Order save(Order order);
    Optional<Order> getById(long id);
    boolean delete(long id);
}
