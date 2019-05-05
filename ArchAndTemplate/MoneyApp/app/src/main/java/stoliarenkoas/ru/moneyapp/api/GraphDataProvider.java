package stoliarenkoas.ru.moneyapp.api;

import android.support.annotation.NonNull;

import java.util.Map;

public interface GraphDataProvider {

    @NonNull
    public Map<String, Double> getGraphData();

}
