package ru.stoliarenko.gb.spring.mvc.dao;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.stoliarenko.gb.spring.mvc.configuration.DataGenerator;
import ru.stoliarenko.gb.spring.mvc.entity.Product;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Product> findAll(String name, Long cost) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Product> query = criteriaBuilder.createQuery(Product.class);
        Root<Product> productRoot = query.from(Product.class);
        query.select(productRoot);
        List<Predicate> predicates = new ArrayList<>();
        if (name != null && !name.isEmpty()) {
            predicates.add(criteriaBuilder.like(productRoot.get("title"), "%" + name + "%"));
        }
        if (cost != null) {
            predicates.add(criteriaBuilder.equal(productRoot.get("cost"), cost));
        }
        query.where(predicates.toArray(new Predicate[]{}));
        return em.createQuery(query).getResultList();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(Product product) {
        em.persist(product);
    }

}
