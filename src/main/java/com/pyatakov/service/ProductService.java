package com.pyatakov.service;

import com.pyatakov.entity.Product;
import com.pyatakov.other.objectContainer.product.ProductDTO;

import java.util.List;

public interface ProductService {

    List<Product> findProductsByCategory_Id(Long id);
    Product findById(Long id);
    Product save(Product product);
    void delete(Product product);
    void deleteAll(List<Product> products);

    List<ProductDTO> findProductsByCategory_Id_Dto(Long id);
    ProductDTO findInfoById(Long id);

}
