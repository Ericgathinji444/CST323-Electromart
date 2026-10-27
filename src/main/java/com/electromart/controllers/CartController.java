package com.electromart.controllers;

import com.electromart.models.CartItem;
import com.electromart.models.Product;
import com.electromart.services.ProductService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);
    private final ProductService productService;

    public CartController(ProductService productService) {
        this.productService = productService;
    }

    @SuppressWarnings("unchecked")
    private List<CartItem> getCart(HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    @GetMapping
    public String viewCart(HttpSession session, Model model) {
        logger.info("Entering viewCart()");
        List<CartItem> cart = getCart(session);
        
        // Safely calculate total
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem item : cart) {
            total = total.add(item.getSubtotal());
        }
                
        model.addAttribute("cartItems", cart);
        model.addAttribute("total", total);
        logger.info("Exiting viewCart(), total={}", total);
        return "cart/view";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId, HttpSession session) {
        logger.info("Entering addToCart() for productId: {}", productId);
        Product product = productService.getProductById(productId);
        List<CartItem> cart = getCart(session);

        boolean exists = false;
        for (CartItem item : cart) {
            if (item.getProductId().equals(productId)) {
                item.setQuantity(item.getQuantity() + 1);
                exists = true;
                break;
            }
        }
        
        if (!exists) {
            cart.add(new CartItem(product.getProductId(), product.getName(), product.getPrice(), 1));
        }
        
        logger.info("Exiting addToCart(), cart size: {}", cart.size());
        return "redirect:/cart"; 
    }

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam Long productId, HttpSession session) {
        logger.info("Entering removeFromCart() for productId: {}", productId);
        List<CartItem> cart = getCart(session);
        cart.removeIf(item -> item.getProductId().equals(productId));
        return "redirect:/cart";
    }
}