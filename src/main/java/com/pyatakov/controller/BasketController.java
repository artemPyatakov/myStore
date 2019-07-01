package com.pyatakov.controller;


import com.pyatakov.component.Basket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping(value = "/basket")
public class BasketController {

     @Autowired
     private Basket basket;
        
     @PutMapping("/public/put")
     public void put(@RequestBody List<Basket.ProductsOrder> productList, HttpSession httpSession){
         httpSession.setMaxInactiveInterval(3600*24);
         basket.getProductIdList().clear();
         basket.getProductIdList().addAll(productList);
         basket.setProductIdList(productList);
     }

     @PutMapping("/public/clear")
     public void clear(HttpSession httpSession) {
         httpSession.setMaxInactiveInterval(3600*24);
         basket.getProductIdList().clear();
     }

     @GetMapping("/public/get")
     public List<Basket.ProductsOrder> get(HttpSession httpSession) {
         httpSession.setMaxInactiveInterval(3600*24);
         return basket.getProductIdList();
     }


}
