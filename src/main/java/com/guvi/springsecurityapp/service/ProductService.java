package com.guvi.springsecurityapp.service;

import com.guvi.springsecurityapp.dto.Product;
import com.guvi.springsecurityapp.entity.Userinfo;
import com.guvi.springsecurityapp.repository.UserInfoRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ProductService {

    //list of productref product object
    List<Product> productList=null;

    //inject repo and password encoder into service layer
    @Autowired
    private UserInfoRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;



    //generate set of random productList->50
    @PostConstruct
    public void loadProductFromDB(){
        productList= IntStream.rangeClosed(1,80)
                        .mapToObj(i->Product.builder()
                        .productId(i)
                        .name("product " +i)
                        .qty(new Random().nextInt(10))
                        .price(new Random().nextInt(8000)).build()
                ).collect(Collectors.toList());
                //collect product object into list
    }


    //method to get all products
    public  List<Product> getProducts(){
        return productList;
    }



    //method to get  product by an id->filter method
    public  Product getProduct(int id){
        return productList.stream()
                .filter(product -> product.getProductId()==id)
                .findAny()
                .orElseThrow(()->new RuntimeException("product "+ id +" not available"));
    }



    //create method to add user details
    public String addUser(Userinfo userinfo){
        //password must stored encoded format->hashcode format
        userinfo.setPassword(passwordEncoder.encode(userinfo.getPassword()));
        repository.save(userinfo);
        return "User Added to the database successfully";
    }


}
