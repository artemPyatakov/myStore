package com.pyatakov.repository;

import com.pyatakov.entity.Product;
import com.pyatakov.other.objectContainer.product.ProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository
        extends JpaRepository<Product, Long> {

    List<Product> findProductsByCategory_Id(Long id);

    @Query("select new com.pyatakov.other.objectContainer.product.ProductDTO(p.id, p.name, p.price) " +
           "from Product p " +
           "join p.category c where c.id = :id")
    List<ProductDTO> findProductsByCategory_Id_Dto(@Param("id") Long id);

    @Query("select new com.pyatakov.other.objectContainer.product.ProductDTO(p.info) " +
           "from Product p " +
           "where p.id = :id")
    ProductDTO findInfoById(@Param("id") Long id);
        
}
