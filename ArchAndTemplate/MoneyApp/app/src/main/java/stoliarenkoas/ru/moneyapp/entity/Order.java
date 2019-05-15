package stoliarenkoas.ru.moneyapp.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Order {

    private long time = -1L;
    private Category type = Category.MISCELLANEOUS;
    private String description = "no description";
    private double price = 0D;

    public Order(){}

}
