package hr.spring.web.trisek.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemDTO {
    private Integer id;
    private String name;
    private String description;
    private Integer quantity;
    private Integer categoryId;
    private String categoryName;
}
