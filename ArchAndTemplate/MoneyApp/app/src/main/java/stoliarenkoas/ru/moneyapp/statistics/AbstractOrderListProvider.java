package stoliarenkoas.ru.moneyapp.statistics;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import stoliarenkoas.ru.moneyapp.api.OrderListProvider;
import stoliarenkoas.ru.moneyapp.api.OrderRepisitory;
import stoliarenkoas.ru.moneyapp.entity.Order;

public abstract class AbstractOrderListProvider implements OrderListProvider {

    @Nullable
    protected OrderRepisitory orderRepisitory;

    @NonNull
    protected final List<Order> orderList = new ArrayList<>();

}
