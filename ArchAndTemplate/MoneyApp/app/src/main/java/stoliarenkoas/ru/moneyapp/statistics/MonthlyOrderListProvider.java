package stoliarenkoas.ru.moneyapp.statistics;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Collection;
import java.util.List;

import stoliarenkoas.ru.moneyapp.api.OrderRepisitory;
import stoliarenkoas.ru.moneyapp.entity.Order;

public class MonthlyOrderListProvider extends AbstractOrderListProvider {

    private static final long MONTH = 1000L * 3600 * 24 * 30;

    public MonthlyOrderListProvider(@Nullable final OrderRepisitory orderRepisitory) {
        this.orderRepisitory = orderRepisitory;
    }

    @NonNull
    @Override
    public List<Order> getOrderList() {
        if (orderRepisitory == null) return orderList;
        orderList.clear();
        @NonNull final Collection<Order> allOrders = orderRepisitory.findAll();
        final long currentTime = System.currentTimeMillis();
        for (@Nullable final Order order : allOrders) {
            if (order == null) continue;
            if ((currentTime - order.getTime()) <= MONTH) orderList.add(order);
        }
        return orderList;
    }

}
