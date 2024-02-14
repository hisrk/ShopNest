package com.shopnest.major.service;

import com.shopnest.major.model.Product;
import com.shopnest.major.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {



    @Autowired
    ProductRepository productRepository;

//    public ProductService(ProductRepository productRepository) {
//        this.productRepository = productRepository;
//    }

    public List<Product> getAllProducts() {

            return  productRepository.findAll();

    }





    public void addProducts(Product product) {



        productRepository.save(product);


    }

      public void deleteProductById(long id){

           productRepository.deleteById(id);

      }


      public Optional<Product> getProductById(long id){

        return productRepository.findById(id);
      }


      public List<Product> getAllProductsByCategoryId(int id){


        return   productRepository.findAllByCategory_Id(id);
      }
}
