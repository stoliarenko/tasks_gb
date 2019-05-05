package stoliarenkoas.ru.moneyapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import stoliarenkoas.ru.moneyapp.entity.Category;
import stoliarenkoas.ru.moneyapp.entity.Order;
import stoliarenkoas.ru.moneyapp.entity.OrderBuilder;

public class MainActivity extends AppCompatActivity {

    private List<Order> orderList = new ArrayList<>();

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OrderBuilder builder = new OrderBuilder();
        builder.setCategory(Category.FOOD_BASIC.name())
                .setDescription("descrrrffvd")
                .setPrice(666.000);
        orderList.add(builder.makeOrder());
        orderList.add(builder.makeOrder());
        orderList.add(builder.makeOrder());

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_orders);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(orderList);
        recyclerView.setAdapter(mAdapter);
    }
}
