package ru.stoliarenko.gb.spring.mvc.repository;

import org.springframework.stereotype.Repository;
import ru.stoliarenko.gb.spring.mvc.entity.Product;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ProductRepository {

    private Map<String, Product> db = new HashMap<>();

    public Collection<Product> findAll() {
        return db.values();
    }

    public Product findOne(String id) {
        return db.get(id);
    }

    public void save(Product product) {
        db.put(product.getId(), product);
    }

    @PostConstruct
    public void init() {
        Product productOne = new Product();
        productOne.setTitle("Toy car");
        productOne.setCost(12000L);

        Product productTwo = new Product();
        productTwo.setTitle("Doll");
        productTwo.setCost(21000L);

        Product productThree = new Product();
        productThree.setTitle("LEGO");
        productThree.setCost(66600L);

        save(productOne);
        save(productTwo);
        save(productThree);
    }

}
