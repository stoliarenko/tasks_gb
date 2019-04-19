package stoliarenkoas.ru.moneyapp.entity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class OrderBuilder {

    @NonNull
    private final Order order = new Order(System.currentTimeMillis());

    public OrderBuilder setTime(long time) {
        order.setTime(time);
        return this;
    }

    public OrderBuilder setCategory(@Nullable final String category) {
        if (category == null || category.isEmpty()) throw new IllegalArgumentException("invalid category");
        for (Category type : Category.values()) {
            if (category.equalsIgnoreCase(type.name())) {
                order.setType(type);
                break;
            }
        }
        return this;
    }

    public OrderBuilder setDescription(@Nullable final String description) {
        if (description == null) throw new NullPointerException("null description");
        order.setDescription(description);
        return this;
    }

    public OrderBuilder setPrice(double price) {
        if (price < 0) throw new IllegalArgumentException("price can't be negative");
        order.setPrice(price);
        return this;
    }

    public Order makeOrder() {
        return order;
    }

}
