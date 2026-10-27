package com.electromart;

import com.electromart.models.Product;
import com.electromart.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class DataSeeder implements CommandLineRunner {
    private final ProductRepository repo;

    public DataSeeder(ProductRepository repo) {
        this.repo = repo;
    }

    @Override
    public void run(String... args) {
        if (repo.count() == 0) {
            Product p1 = new Product();
            p1.setName("Dell XPS 15");
            p1.setPrice(new BigDecimal("1499.99"));
            p1.setStock(10);
            repo.save(p1);

            Product p2 = new Product();
            p2.setName("MacBook Pro");
            p2.setPrice(new BigDecimal("1999.99"));
            p2.setStock(5);
            repo.save(p2);
        }
    }
}