package stoliarenkoas.ru.moneyapp.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import stoliarenkoas.ru.moneyapp.entity.Category;
import stoliarenkoas.ru.moneyapp.entity.Order;

public interface OrderDB {

    void insert(@Nullable final Order order);

    void update(@Nullable final Order order);

    void delete(@Nullable final Order order);

    @NonNull
    List<Order> selectAll();

    @Nullable
    Order findByTime(long time);

    @NonNull
    List<Order> findByTimeInterval(long startTime, long endTime);

    @NonNull
    List<Order> findByCategory(@Nullable final Category category);

}
