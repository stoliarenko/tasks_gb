package ru.stoliarenko.gb.spring.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import ru.stoliarenko.gb.spring.mvc.entity.Product;
import ru.stoliarenko.gb.spring.mvc.repository.ProductRepository;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/all")
    public String list(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "product-list";
    }

    @PostMapping("/create")
    public RedirectView create(@RequestParam("title") String title, @RequestParam("cost") Long cost) {
        Product product = new Product();
        product.setTitle(title);
        product.setCost(cost);
        productRepository.save(product);
        return new RedirectView("/mvc/product/all");
    }

}
