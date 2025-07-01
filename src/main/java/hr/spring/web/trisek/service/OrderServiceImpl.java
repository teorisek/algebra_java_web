package hr.spring.web.trisek.service;

import hr.spring.web.trisek.model.Order;
import hr.spring.web.trisek.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public List<Order> getAll() {
        return orderRepository.getAll();
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Optional<Order> getById(long id) {
        return orderRepository.getById((int) id);
    }

    @Override
    public boolean delete(long id) {
        return orderRepository.delete((int) id);
    }
}
