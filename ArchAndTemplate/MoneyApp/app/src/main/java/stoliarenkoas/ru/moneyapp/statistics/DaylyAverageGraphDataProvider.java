package stoliarenkoas.ru.moneyapp.statistics;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import stoliarenkoas.ru.moneyapp.api.OrderListProvider;
import stoliarenkoas.ru.moneyapp.entity.Order;

public class DaylyAverageGraphDataProvider extends AbstractGraphDataProvider {

    public DaylyAverageGraphDataProvider(@Nullable final OrderListProvider orderListProvider) {
        this.orderListProvider = orderListProvider;
    }

    @NonNull
    @Override
    public Map<String, Double> getGraphData() {
        if (orderListProvider == null) return graphData;
        @NonNull final List<Order> orderList = orderListProvider.getOrderList();

        for (@Nullable final Order order : orderList) {
            if (order == null) continue;
            final String key = getDay(order.getTime());
            Double value = graphData.get(key);
            value = value == null ? order.getPrice() : value + order.getPrice();
            graphData.put(key, value);
        }

        return graphData;
    }

    private static final String UNKNOWN_DAY = "unknown day";
    private static final String TODAY = "today";
    private static final String DAY_PATTERN = "%d days before";

    @NonNull
    private static String getDay(@Nullable final Long time) {
        if (time == null) return UNKNOWN_DAY;
        final long difference = System.currentTimeMillis() - time;
        if (difference < 0) return UNKNOWN_DAY;
        final long daysCount = difference / (24 * 3600 * 1000);

        if (daysCount == 0) return TODAY;
        return String.format(Locale.ENGLISH, DAY_PATTERN, daysCount);
    }

}
