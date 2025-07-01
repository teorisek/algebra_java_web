package hr.spring.web.trisek.model;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import java.util.*;

@Component
@SessionScope
public class Cart {
    private final Map<Integer, Integer> items = new HashMap<>();

    public Map<Integer, Integer> getItems() {
        return items;
    }

    public void addItem(int itemId, int quantity) {
        items.merge(itemId, quantity, Integer::sum);
    }

    public void updateItem(int itemId, int quantity) {
        if (quantity <= 0) items.remove(itemId);
        else items.put(itemId, quantity);
    }

    public void removeItem(int itemId) {
        items.remove(itemId);
    }

    public void clear() {
        items.clear();
    }
}
