package com.shopnest.major.controller;


import com.shopnest.major.global.GlobalData;
import com.shopnest.major.service.CategoryService;
import com.shopnest.major.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;


    //THIS BASICALLY DEALS WITH INDEX

    @GetMapping({"/","/home"})
    public String homePage(Model model){

        model.addAttribute("cartCount",GlobalData.cart.size());

        return "index";
    }

    @GetMapping("/shop")
    public String shop(Model model){

            model.addAttribute("categories",categoryService.getAllCategories());
            model.addAttribute("products"  ,productService.getAllProducts());
        model.addAttribute("cartCount", GlobalData.cart.size());


            return "shop";



    }
    @GetMapping("/shop/category/{id}")
    public String getProductByCategoryId(@PathVariable int id,Model model){
//ALL CATEEGORIES ARE GOING TO BE DISPLAYED BUT PRODUCT WILL BE DISPLAYED ON THE BASIS OF PRODUCT

                model.addAttribute("categories",categoryService.getAllCategories());
                model.addAttribute("products",productService.getAllProductsByCategoryId(id));
        model.addAttribute("cartCount",GlobalData.cart.size());


                return "shop";
    }

    @GetMapping("/shop/viewproduct/{id}")

    public String viewProduct(Model model,@PathVariable long id){



        model.addAttribute("product",productService.getProductById(id).get());//BECAUSE IT IS RETURNING OPTIONAL);
        model.addAttribute("cartCount",GlobalData.cart.size());

        return "viewProduct";


    }









}
