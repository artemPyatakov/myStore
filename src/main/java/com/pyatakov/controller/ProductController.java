package com.pyatakov.controller;


import com.pyatakov.entity.Category;
import com.pyatakov.entity.Product;
import com.pyatakov.other.objectContainer.product.ProductDTO;
import com.pyatakov.other.objectContainer.product.Products;
import com.pyatakov.service.CategoryService;
import com.pyatakov.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.RoundingMode;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    final Logger logger =
            LoggerFactory.getLogger(ProductController.class);

    @GetMapping(value = "/public/findByCategoryId/{id}")
    public Products findByCategory(@PathVariable Long id) {
        return new Products(productService.findProductsByCategory_Id_Dto(id));
    }

    @GetMapping(value = "/public/findInfoById/{id}")
    public ProductDTO findInfoById(@PathVariable Long id) {
        return productService.findInfoById(id);
    }

    @PostMapping(value = "/private/createProductInCategory/{id}")
    public Product create(@RequestBody Product product, @PathVariable Long id) {
        logger.info("Creating product: ");
        Category category = categoryService.findById(id);
        product.setCategory(category);
        Product createdProduct =  productService.save(product);
        logger.info("Product created successfully with info: " + createdProduct);
        return productService.save(createdProduct);
    }

    @PutMapping(value = "/private/update")
    public void update(@RequestBody Product product) {
        logger.info("Updating product with id: " + product.getId());
        if (product.getId()== null)
            logger.info("This method cannot create product");
        else {
            try {
                Product productUpdate = productService.findById(product.getId());
                productUpdate.setName(product.getName());
                productUpdate.setPrice(product.getPrice().setScale(2, RoundingMode.HALF_UP));
                productUpdate.setInfo(product.getInfo());
                System.out.println(productUpdate);

                productService.save(productUpdate);
                logger.info("Product successfully updated with info: " + product);
            } catch (NoSuchElementException e){
                logger.info("Cannot find product with id: " + product.getId());
            }
        }
    }

    @DeleteMapping(value = "/private/delete/{id}")
    public void delete(@PathVariable Long id){
        logger.info("Deleting singer with id: " + id);
        Product product = productService.findById(id);
        productService.delete(product);
        logger.info("Product deleted successfully");
    }

    @DeleteMapping(value = "/private/deleteByCategoryId/{id}")
    public void deleteByCategoryId(@PathVariable Long id){
        logger.info("Deleting singer with category id: " + id);
        List<Product> products = productService.findProductsByCategory_Id(id);
        productService.deleteAll(products);
        logger.info("Product deleted successfully");
    }

}
