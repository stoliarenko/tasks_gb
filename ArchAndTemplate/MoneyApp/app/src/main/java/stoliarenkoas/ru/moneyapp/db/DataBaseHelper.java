package stoliarenkoas.ru.moneyapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "money.db";
    private static final int DB_VERSION = 1;

    static final String TABLE_ORDERS = "orders";

    DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder sb = new StringBuilder();
        sb.append(OrderTableColumns.TIME.columnName);
        sb.append(" INTEGER PRIMARY KEY, ");
        sb.append(OrderTableColumns.CATEGORY.columnName);
        sb.append(" TEXT, ");
        sb.append(OrderTableColumns.DESCRIPTION.columnName);
        sb.append(" TEXT, ");
        sb.append(OrderTableColumns.PRICE.columnName);
        sb.append(" REAL");

        final String request = String.format("CREATE TABLE %s (%s);", TABLE_ORDERS, sb.toString());
        db.execSQL(request);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //You won't need to migrate if u never release .jpg
    }
}
