package ru.stoliarenko.gb.spring.mvc.configuration;

import ru.stoliarenko.gb.spring.mvc.entity.Product;

import java.util.ArrayList;
import java.util.List;

public abstract class DataGenerator {

    public static List<Product> generateProducts() {
        final List<Product> result = new ArrayList<>();
        for (int i = 1; i < 21; i++) {
            Product product = new Product();
            product.setTitle("Product#" + i);
            product.setCost((long) (i % 3 + 1) * 10);
            result.add(product);
        }
        return result;
    }

}
