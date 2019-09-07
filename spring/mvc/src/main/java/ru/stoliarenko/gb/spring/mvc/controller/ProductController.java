package ru.stoliarenko.gb.spring.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import ru.stoliarenko.gb.spring.mvc.configuration.DataGenerator;
import ru.stoliarenko.gb.spring.mvc.dao.ProductDao;
import ru.stoliarenko.gb.spring.mvc.entity.Product;

import javax.annotation.PostConstruct;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductDao productDao;

    @GetMapping("/all")
    public String list(
            Model model,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "cost", required = false) Long cost
    ) {
        model.addAttribute("products", productDao.findAll(title, cost));
        return "product-list";
    }

    @PostMapping("/create")
    public RedirectView create(@RequestParam("title") String title, @RequestParam("cost") Long cost) {
        Product product = new Product();
        product.setTitle(title);
        product.setCost(cost);
        productDao.save(product);
        return new RedirectView("/mvc/product/all");
    }

    @PostConstruct
    public void init() {
        DataGenerator.generateProducts().forEach(productDao::save);
    }

}
