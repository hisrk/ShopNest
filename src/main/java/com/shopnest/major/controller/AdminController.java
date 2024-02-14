package com.shopnest.major.controller;

import com.shopnest.major.model.Category;
import com.shopnest.major.model.Product;
import com.shopnest.major.payload.ProductDto;
import com.shopnest.major.service.CategoryService;
import com.shopnest.major.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping
public class AdminController {

//IMPORTANT--> JAB humne user.dir lihe to vo hame diretlt src me dal deta hai
    public static String uploadDir= System.getProperty("user.dir")+"/src/main/resources/static/productImages";


    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;


    @GetMapping("/admin")
    public String adminHome() {


        return "adminHome";


    }

    @GetMapping("/admin/categories")
    public String getCategories(Model model) {
        List<Category> categories = categoryService.getAllCategories();

        model.addAttribute("categories", categories);
        return "categories";
    }


    @GetMapping("/admin/categories/add")
    public String getCatergoryNew(Model model) {


        model.addAttribute("category", new Category());


        return "categoriesAdd";
    }


    @PostMapping("/admin/categories/add")//category should be same always as we are giving category in front end
    public String addCategory(@ModelAttribute("category") Category category) {

        categoryService.addCategory(category);


        return "redirect:/admin/categories";
    }


    @GetMapping("/admin/categories/delete/{id}")
    public String deleteCategory(@PathVariable int id, Model model) {

        categoryService.deleteCategory(id);


        return "redirect:/admin/categories";

    }

    @GetMapping("/admin/categories/update/{id}")
    public String updateCategory(@PathVariable int id, Model model) {

        Optional<Category> category = categoryService.getCategoryById(id);


        if (category.isPresent()) {


            model.addAttribute("category", category.get());


            return "categoriesAdd";
        }

        return "404";
    }

//PRODUCT DETAILS ADDITION STARTS HERE


    @GetMapping("/admin/products")
    public String getProducts(Model model) {

        List<Product> products = productService.getAllProducts();


        model.addAttribute("products", products);


        return "products";
    }


    @GetMapping("/admin/products/add")
    public String addProducts(Model model) {


        model.addAttribute("productDTO", new ProductDto());
//isme hum sirf ek new dto object supply kar rahe ke jisme sara data dal sake
        model.addAttribute("categories", categoryService.getAllCategories());

        //jitne bhi hamare pas categories the wo hamne retrive karke bhej die

        //WHY WE ARE PROVIDING CATEGORIES IN THIS SECTION
        //BECAUSE ON THE BASIS OF CATEGRIES ONLY WE WILLL HAVE NEW PRODUCT


        return "productsAdd";
    }


    @PostMapping("/admin/products/add")//save route but for  post only
//New thing how to get the image -->MULTIPART form data TO BE USED WHENEVER WE ARE DEALING WITH ANY TYPE OF FILE
//We have not provided the storing in ProductDto so any varibale which are not wrapping in object can get  y Requestparam
    //Exception IS THROWN BEACAUSE FILE HANDLING IS HAPPENING
    //MULTIPARTFILE-IS AN INTERFACE IN WHICH METHDOS ARS PRESENT

    //--->1-->isEmpty-->checks if file is empty or not
    //--->2-->getOriginalName -->retrieves the original name of file
    //--->3-->
    //--->1-->isEmpty

    public String addNewProduct(@ModelAttribute("productDTO") ProductDto productDTO,
                                @RequestParam("productImage") MultipartFile multiPartFile
            , @RequestParam("imgName") String imageName) throws IOException {


        Product product = new Product();
        product.setName(productDTO.getName());
        product.setId(productDTO.getId());
        //isme pehel me id get kar arah hu fir i am checking ki id exixts kar rahi hai ki nahin
        product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());//Beacuse it will retuen optional that why we are using get method at last
        product.setPrice(productDTO.getPrice());
        product.setWeight(productDTO.getWeight());
        product.setDescription(productDTO.getDescription());

//WE HAVE TO MAKE TWO PARTS OF IAMGE ONE IS STORED IN DATABASES AND OTHE IS IN STATIC

        String imageUUID;

        //Now we have to check wheter the user loaded the file succsfully or not
        //is empty jab false hogi to hame milega ke usme file present hai
        if(!multiPartFile.isEmpty()){


            //Now we will fill UUId
//now we will get the original file name
            imageUUID= multiPartFile.getOriginalFilename();

            //AB HUME FILE KO SAVE KARANA HAI PRODUCT IMAGES ME TO HAMARE PAS EK PATH CLASS HAI
            //NIO SE PATH AEGA
            //Upload directory will hold the path od this imageUUID
            Path fileNameAndPath= Paths.get(uploadDir,imageUUID);

            Files.write(fileNameAndPath,multiPartFile.getBytes());


        }else {
            //WHEN FILE IS EMPTY
            imageUUID=imageName;

        }
        product.setImageName(imageUUID);


            productService.addProducts(product);




        return "redirect:/admin/products";


    }

    @GetMapping("/admin/product/delete/{id}")
    public String deleteProduct(@PathVariable long id){

        productService.deleteProductById(id);


        return "redirect:/admin/products";
    }

@GetMapping("/admin/product/update/{id}")
    public String updateproduct(@PathVariable long id,Model model){

Product product= productService.getProductById(id).get();

      ProductDto productDto= new ProductDto();
      productDto.setId(product.getId());
      productDto.setName(product.getName());
      productDto.setPrice(product.getPrice());
      productDto.setWeight(product.getWeight());
      productDto.setDescription(product.getDescription());
      productDto.setCategoryId(product.getCategory().getId());
      productDto.setImageName(product.getImageName());
  model.addAttribute("productDTO",productDto);
model.addAttribute("categories",categoryService.getAllCategories());





   return "productsAdd";
}






}
