package hr.spring.web.trisek.model;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class CheckoutForm {
    private Integer id;
    private String customerName;
    private String email;
    private String address;
    private BigDecimal amount;
    private String paymentMethod;
    private String paymentStatus;
}