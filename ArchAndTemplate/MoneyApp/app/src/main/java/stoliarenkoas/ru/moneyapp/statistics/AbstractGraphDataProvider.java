package stoliarenkoas.ru.moneyapp.statistics;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

import stoliarenkoas.ru.moneyapp.api.GraphDataProvider;
import stoliarenkoas.ru.moneyapp.api.OrderListProvider;

public abstract class AbstractGraphDataProvider implements GraphDataProvider {

    @Nullable
    protected OrderListProvider orderListProvider;

    @NonNull
    protected final Map<String, Double> graphData = new LinkedHashMap<>();

}
