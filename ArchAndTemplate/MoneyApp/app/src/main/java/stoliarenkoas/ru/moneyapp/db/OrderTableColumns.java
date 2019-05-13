package stoliarenkoas.ru.moneyapp.db;

public enum OrderTableColumns {

    TIME("id"),
    CATEGORY("category"),
    DESCRIPTION("description"),
    PRICE("price");

    OrderTableColumns(final String columnName) {
        this.columnName = columnName;
    }

    public final String columnName;

}
