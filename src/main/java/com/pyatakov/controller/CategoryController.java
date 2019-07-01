package com.pyatakov.controller;

import com.pyatakov.component.validator.CategoryValidator;
import com.pyatakov.entity.Category;
import com.pyatakov.other.objectContainer.Categories;
import com.pyatakov.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/category")
public class CategoryController {

    final Logger logger =
            LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryValidator categoryValidator;

    @GetMapping(value = "/public/listData")
    public Categories listData() {
         return new Categories(categoryService.findAll());
    }

    @PostMapping(value = "/private/create")
    public Category create(@RequestBody Category category) {
        logger.info("Creating category: ");

        BeanPropertyBindingResult result =
                new BeanPropertyBindingResult(category, "CategoryController.create.error");

        ValidationUtils.invokeValidator(
                categoryValidator, category , result
        );
        List<ObjectError> errors = result.getAllErrors();
        if (errors.size()>0) {
            logger.info("Validation errors:");
            errors.forEach(e -> logger.info(e.getObjectName()+ ": "
                    + e.getCode() + ". " + e.getDefaultMessage()));
        } else {
            Category createdCategory = categoryService.save(category);
            logger.info("Category created successfully with info: " + createdCategory);
            return createdCategory;
        }
        return null;
    }

    @DeleteMapping(value = "/private/delete/{id}")
    public void delete(@PathVariable Long id) {
        logger.info("Deleting singer with id: " + id);
        Category category = categoryService.findById(id);
        categoryService.delete(category);
        logger.info("Category deleted successfully");
    }
    
    @PutMapping(value = "/private/update")
    public void update(@RequestBody Category category) {
        logger.info("Updating category with id: " + category.getId());
        if (category.getId()== null)
            logger.info("This method cannot create category");
        else {
            try {
                Category categoryUpdate = categoryService.findById(category.getId());
                categoryUpdate.setName(category.getName());
                categoryService.save(categoryUpdate);

                logger.info("Category successfully updated with info: " + category);
            } catch (NoSuchElementException e){
                logger.info("Cannot find category with id: " + category.getId());
            }                                
        }
    }

}
