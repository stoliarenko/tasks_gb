package stoliarenkoas.ru.moneyapp.api;

import android.support.annotation.NonNull;

import java.util.List;

import stoliarenkoas.ru.moneyapp.entity.Order;

public interface OrderListProvider {

    @NonNull
    public List<Order> getOrderList();

}
