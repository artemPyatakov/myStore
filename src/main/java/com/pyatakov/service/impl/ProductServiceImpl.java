package com.pyatakov.service.impl;

import com.google.common.collect.Lists;
import com.pyatakov.entity.Product;
import com.pyatakov.other.objectContainer.product.ProductDTO;
import com.pyatakov.repository.ProductRepository;
import com.pyatakov.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("productService")
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Product> findProductsByCategory_Id(Long id) {
        return Lists.newArrayList(productRepository.findProductsByCategory_Id(id));
    }

    @Transactional(readOnly = true)
    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).get();
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void delete(Product product) {
       productRepository.delete(product);
    }

    @Override
    public void deleteAll(List<Product> products) {
        productRepository.deleteAll(products);
    }

    @Override
    public List<ProductDTO> findProductsByCategory_Id_Dto(Long id) {
         return Lists.newArrayList(productRepository.findProductsByCategory_Id_Dto(id));
    }

    @Override
    public ProductDTO findInfoById(Long id) {
        return productRepository.findInfoById(id);
    }
}
