package com.electromart.controllers;

import com.electromart.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    private final ProductService productService;

    public HomeController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String home(Model model) {
        logger.info("Entering home() controller method");
        
        // Fetch products from the service and add them to the model
        model.addAttribute("products", productService.getAllProducts());
        
        logger.info("Exiting home() controller method");
        
        // This returns the name of the HTML file (home.html) to be rendered
        return "home";
    }
}