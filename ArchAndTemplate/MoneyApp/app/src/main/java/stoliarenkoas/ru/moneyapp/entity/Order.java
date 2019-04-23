package stoliarenkoas.ru.moneyapp.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {

    private long time;
    private Category type;
    private String description;
    private double price;

    Order(){}

}
