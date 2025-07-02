package hr.spring.web.trisek.service;

import org.springframework.stereotype.Service;

@Service
public class PayPalService {

    public boolean verifyOrder(String paypalOrderId) {
        // TODO: Implement PayPal REST API call to verify orderId
        // For now, just accept any non-null orderId
        return paypalOrderId != null && !paypalOrderId.isEmpty();
    }
}
