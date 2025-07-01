package hr.spring.web.trisek.repository;

import hr.spring.web.trisek.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    List<Order> getAll();
    Order save(Order order);
    boolean delete(Integer id);
    Optional<Order> getById(Integer id);
}