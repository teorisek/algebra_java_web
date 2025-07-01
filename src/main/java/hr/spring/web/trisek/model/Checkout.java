package hr.spring.web.trisek.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Checkout {
    private Integer id;
    private String customerName;
    private String email;
    private String address;
    private BigDecimal amount;
    private String paymentMethod;
    private String paymentStatus;
}
