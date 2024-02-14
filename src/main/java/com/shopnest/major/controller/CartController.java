package com.shopnest.major.controller;

import com.shopnest.major.global.GlobalData;
import com.shopnest.major.model.Product;
import com.shopnest.major.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CartController {


    @Autowired
    private ProductService productService;

    @GetMapping("/addToCart/{id}")
    public String addTocart(@PathVariable long id){

        GlobalData.cart.add(productService.getProductById(id).get());

        return "redirect:/shop";





    }

    @GetMapping("/cart")
    public String getcart(Model model){
//sab jagah bhejna hai ye
        model.addAttribute("cartCount",GlobalData.cart.size());

        model.addAttribute("total",GlobalData.cart.stream().mapToDouble(Product::getPrice));
        //mapToDouble(): This method is used to transform each element of the stream into a primitive double value. In this case, it's calling the getPrice() method on each Product object to retrieve its price.
        //
        //Product::getPrice: This is a method reference to the getPrice() method defined in the Product class. It's a shorthand way of writing a lambda expression that invokes the getPrice() method.
        //
        //DoubleStream: After applying mapToDouble(), the resulting stream is a DoubleStream, which is a specialized stream for handling primitive double values.



        model.addAttribute("cart",GlobalData.cart);



        return "cart";
    }

    @GetMapping("/cart/removeItem/{index}")
    public String cartItemRemove(@PathVariable int index){


        GlobalData.cart.remove(index);

        return "redirect:/cart";

    }

    @GetMapping("/checkout")

    public String checkout(Model model){

        model.addAttribute("total",GlobalData.cart.stream().mapToDouble(Product::getPrice));

        return "checkout";
    }


}
