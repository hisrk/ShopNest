package com.shopnest.major.service;

import com.shopnest.major.model.Category;
import com.shopnest.major.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {



   @Autowired
    CategoryRepository categoryRepository;

    public void addCategory(Category category) {





        categoryRepository.save(category);


    }

    public List<Category> getAllCategories() {



                    return categoryRepository.findAll();

    }


    public void deleteCategory(int id) {

          categoryRepository.deleteById(id);


    }

    public Optional<Category> getCategoryById(int id) {

        return  categoryRepository.findById(id);






    }
}
