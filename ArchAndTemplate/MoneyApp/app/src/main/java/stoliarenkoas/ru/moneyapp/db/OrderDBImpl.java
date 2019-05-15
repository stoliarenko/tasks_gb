package stoliarenkoas.ru.moneyapp.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import stoliarenkoas.ru.moneyapp.api.OrderDB;
import stoliarenkoas.ru.moneyapp.entity.Category;
import stoliarenkoas.ru.moneyapp.entity.Order;

public final class OrderDBImpl implements Closeable, OrderDB {

    private final Map<Long, Order> identityMap = new LinkedHashMap<>();

    private final List<Order> orders = new ArrayList<>();
    private static final String[] allColumns;

    private final DataBaseHelper dbHelper;
    private SQLiteDatabase database;
    private Cursor cursor;

    static {
        OrderTableColumns[] values = OrderTableColumns.values();
        allColumns = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            allColumns[i] = values[i].columnName;
        }
    }

    public OrderDBImpl(final DataBaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void open() {
        if (database == null || !database.isOpen()) {
            database = dbHelper.getWritableDatabase();
        }
        requestCursor();
        cursor.moveToFirst();
    }

    @Override
    public void close() {
        if (cursor != null) cursor.close();
        if (dbHelper != null) dbHelper.close();
    }

    private void requestCursor() {
        cursor = database.query(DataBaseHelper.TABLE_ORDERS, null, null, null, null, null, null);
    }

    private void refreshCursor() {
        int position = cursor.getPosition();
        requestCursor();
        cursor.moveToPosition(position);
    }

    @NonNull
    private Order cursorToOrder() {
        final Order order = new Order();
        order.setTime(cursor.getLong(0));
        order.setType(Category.valueOf(cursor.getString(1)));
        order.setDescription(cursor.getString(2));
        order.setPrice(cursor.getDouble(3));
        return order;
    }

    public void putOrder(@Nullable final Order order) {
        if (order == null || order.getTime() == -1L) return;
        final ContentValues values = new ContentValues();
        values.put(OrderTableColumns.TIME.columnName, order.getTime());
        values.put(OrderTableColumns.CATEGORY.columnName, order.getType().toString());
        values.put(OrderTableColumns.DESCRIPTION.columnName, order.getDescription());
        values.put(OrderTableColumns.PRICE.columnName, order.getDescription());
        database.insertWithOnConflict(DataBaseHelper.TABLE_ORDERS, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        identityMap.put(order.getTime(), order);
    }

    public void deleteOrder(@Nullable final Order order) {
        if (order == null || order.getTime() == -1L) return;
        identityMap.remove(order.getTime());
        database.delete(DataBaseHelper.TABLE_ORDERS, OrderTableColumns.TIME.columnName + " = " + order.getTime(), null);
    }

    public List<Order> getOrders() {
        List<Order> cards = new ArrayList<>();
        for (int i = 0; i < cursor.getCount(); i++) {
            final Order order = cursorToOrder();
            identityMap.put(order.getTime(), order);
            cards.add(order);
            cursor.moveToNext();
        }
        cursor.moveToFirst();
        return cards;
    }

    @Override
    public void insert(@Nullable Order order) {
        putOrder(order);
    }

    @Override
    public void update(@Nullable Order order) {
        putOrder(order);
    }

    @Override
    public void delete(@Nullable Order order) {
        deleteOrder(order);
    }

    @NonNull
    @Override
    public List<Order> selectAll() {
        return getOrders();
    }

    @Nullable
    @Override
    public Order findByTime(long time) {
        getOrders();
        final Order order = identityMap.get(time);
        return order;
    }

    @NonNull
    @Override
    public List<Order> findByTimeInterval(long startTime, long endTime) {
        final List<Order> orders = new ArrayList<>();
        getOrders();
        for (@NonNull final Order order : identityMap.values()) {
            final long orderTime = order.getTime();
            if (orderTime >= startTime && orderTime <= endTime) orders.add(order);
        }
        return orders;
    }

    @NonNull
    @Override
    public List<Order> findByCategory(@Nullable Category category) {
        final List<Order> orders = new ArrayList<>();
        getOrders();
        for (@NonNull final Order order : identityMap.values()) {
            final Category orderCategory = order.getType();
            if (orderCategory == category) orders.add(order);
        }
        return orders;
    }
}
