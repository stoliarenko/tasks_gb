package stoliarenkoas.ru.moneyapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import stoliarenkoas.ru.moneyapp.db.DataBaseHelper;
import stoliarenkoas.ru.moneyapp.db.OrderDBImpl;
import stoliarenkoas.ru.moneyapp.entity.Category;
import stoliarenkoas.ru.moneyapp.entity.Order;
import stoliarenkoas.ru.moneyapp.entity.OrderBuilder;

public class MainActivity extends AppCompatActivity {

    private List<Order> orderList = new ArrayList<>();
    private OrderDBImpl dataBase = new OrderDBImpl(new DataBaseHelper(this));

    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRecyclerView();
        addBtnListener();
//        OrderBuilder builder = new OrderBuilder();
//        builder.setCategory(Category.FOOD_BASIC.name())
//                .setDescription("descrrrffvd")
//                .setPrice(666.000);
//        orderList.add(builder.makeOrder());
//        orderList.add(builder.makeOrder());
//        orderList.add(builder.makeOrder());
    }

    private void initRecyclerView(){
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_orders);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mAdapter = new MyAdapter(orderList);
        mAdapter.setOnClickListener(new MyAdapter.OnClickListener() {
            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(MainActivity.this, "This will lead to edit page soon... maybe", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onShortClick(View view, int position) {
                Toast.makeText(MainActivity.this, "This will lead to description page soon... maybe", Toast.LENGTH_LONG).show();
            }
        });

        recyclerView.setAdapter(mAdapter);
    }

    private void addBtnListener() {
        final Button button = (Button) findViewById(R.id.button_add_order);
        final EditText description = findViewById(R.id.field_description);
        final EditText type = findViewById(R.id.field_type);
        final EditText price = findViewById(R.id.field_price);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final OrderBuilder builder = new OrderBuilder();
                builder.setCategory(type.getText().toString())
                        .setDescription(description.getText().toString())
                        .setPrice(Double.parseDouble(price.getText().toString()));
                final Order order = builder.makeOrder();
                orderList.add(order);
                dataBase.putOrder(order);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        dataBase.open();
        if (orderList.isEmpty()) orderList.addAll(dataBase.getOrders());
    }

    @Override
    protected void onPause() {
        super.onPause();
        dataBase.close();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        dataBase.open();
    }



}
