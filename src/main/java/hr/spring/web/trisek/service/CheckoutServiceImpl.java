package hr.spring.web.trisek.service;

import hr.spring.web.trisek.model.Checkout;
import hr.spring.web.trisek.repository.CheckoutRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {

    private final CheckoutRepository checkoutRepository;

    @Override
    public List<Checkout> getAll() {
        return checkoutRepository.getAll();
    }

    @Override
    public Checkout save(Checkout checkout) {
        checkout.setPaymentStatus("PENDING");
        return checkoutRepository.save(checkout);
    }

    @Override
    public Optional<Checkout> getById(long id) {
        return checkoutRepository.getById((int) id);
    }

    @Override
    public boolean delete(long id) {
        return checkoutRepository.delete((int) id);
    }
}
