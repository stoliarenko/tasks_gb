package stoliarenkoas.ru.moneyapp.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Collection;

import stoliarenkoas.ru.moneyapp.entity.Order;

public interface OrderRepisitory {

    @NonNull
    public Collection<Order> findAll();

    @Nullable
    public Order findByTime(@Nullable final Long time);

    @NonNull
    public Collection<Order> findByTimes(@Nullable final Collection<Long> times);

    @Nullable
    public Order merge(@Nullable final Order order);

    void removeByTime(@Nullable final Long time);

    void removeByTimes(@Nullable final Collection<Long> times);

    void remove(@Nullable final Collection<Order> orders);

    void remoceAll();

}
