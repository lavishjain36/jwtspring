package com.guvi.springsecurityapp.controller;


import com.guvi.springsecurityapp.dto.Product;
import com.guvi.springsecurityapp.entity.Userinfo;
import com.guvi.springsecurityapp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    //inject service layer
    @Autowired
    private ProductService service;

    //this is not secure.->bypass
    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome to Our website API endpoint welcome";
    }

    //get all the product
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Product> getAllTheProducts(){
        return service.getProducts();
    }

//get product by the id->user-ramya? can lavish access?->Lavish admin
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public Product getProductById(@PathVariable int id) {
        return service.getProduct(id);
    }


//   http:localhost:8080/products/new->
    @PostMapping("/new")
    public  String addNewUser(@RequestBody Userinfo userinfo){
        return  service.addUser(userinfo);
    }


}
