package controller;


import com.pyatakov.config.DataServiceConfig;
import com.pyatakov.config.TxConfig;
import com.pyatakov.entity.Category;
import com.pyatakov.entity.Product;
import com.pyatakov.other.objectContainer.product.ProductDTO;
import com.pyatakov.other.objectContainer.product.Products;
import restClient.RestClientConfig;
import com.pyatakov.service.CategoryService;
import com.pyatakov.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RestClientConfig.class})
public class ProductRestControllerTest {

    final Logger logger =
            LoggerFactory.getLogger(ProductRestControllerTest.class);

    private static final String URL_GET_PRODUCT_BY_CATEGORY
            = "http://localhost:8080/product/public/findByCategoryId/{id}";
    private static final String URL_GET_INFO
            = "http://localhost:8080/product/public/findInfoById/{id}";
    private static final String URL_CREATE_PRODUCT_IN_CATEGORY
            = "http://localhost:8080/product/private/createProductInCategory/{id}";
    private static final String URL_UPDATE_PRODUCT
            = "http://localhost:8080/product/private/update";
    private static final String URL_DELETE_PRODUCT
            = "http://localhost:8080/product/private/delete/{id}";
    private static final String URL_DELETE_PRODUCT_BY_CATEGOTY_ID
            = "http://localhost:8080/product/private/deleteByCategoryId/{id}";


    @Autowired
    RestTemplate restTemplate;

    GenericApplicationContext ctx;

    @Before
    public void setUp(){
        assertNotNull(restTemplate);
        ctx = new AnnotationConfigApplicationContext(DataServiceConfig.class, TxConfig.class);
    }


    @Test
    public void findByCategoryIdTest(){
        long category_id = 1;
        System.out.println("\n");
        Products products = restTemplate.getForObject(URL_GET_PRODUCT_BY_CATEGORY, Products.class, category_id);
        assertNotNull(products);
        List<ProductDTO> productDTOList = products.getProducts();
        printProductsDTO(productDTOList);
    }

    @Test
    public void findInfoByIdTest(){
        long id = 1;
        System.out.println("\n");
        ProductDTO product = restTemplate.getForObject(URL_GET_INFO, ProductDTO.class, id);
        assertNotNull(product);
        logger.info(product.toString());
    }

    @Test
    public void createAndDeleteTest(){
        System.out.println("\n");
        long id_category = 1;
        Product product = new Product("Test", new BigDecimal(100.91).setScale(2,RoundingMode.HALF_UP),"Test info");
        product = restTemplate.postForObject(URL_CREATE_PRODUCT_IN_CATEGORY, product, Product.class, id_category);
        assertNotNull(product);
        logger.info(product.toString());

        restTemplate.delete(URL_DELETE_PRODUCT, product.getId());

        System.out.println("\n");
        Products products = restTemplate.getForObject(URL_GET_PRODUCT_BY_CATEGORY, Products.class, id_category);
        assertNotNull(products);
        List<ProductDTO> productDTOList = products.getProducts();
        printProductsDTO(productDTOList);
    }

    @Test
    public void updateTest() {
        ProductService productService
                = ctx.getBean(ProductService.class);
        assertNotNull(productService);

        System.out.println("\n");
        Product product = productService.findById(1L);
        assertNotNull(product);
        logger.info(product.toString());

        product.setName("Samsung Galaxy S10 1");
        product.setPrice(new BigDecimal(60990.97).setScale(2, RoundingMode.HALF_UP));

        restTemplate.put(URL_UPDATE_PRODUCT, product);

        System.out.println("\n");
        Products products = restTemplate.getForObject(URL_GET_PRODUCT_BY_CATEGORY, Products.class, 1L);
        assertNotNull(products);
        List<ProductDTO> productDTOList = products.getProducts();
        printProductsDTO(productDTOList);

        product = productService.findById(1L);
        product.setName("Samsung Galaxy S10");
        product.setPrice(new BigDecimal(60990.96).setScale(2, RoundingMode.HALF_UP));

        restTemplate.put(URL_UPDATE_PRODUCT, product);

        System.out.println("\n");
        products = restTemplate.getForObject(URL_GET_PRODUCT_BY_CATEGORY, Products.class, 1L);
        assertNotNull(products);
        productDTOList = products.getProducts();
        printProductsDTO(productDTOList);
    }

    @Test
    public void deleteByCategoryIdTest(){
        CategoryService categoryService
                = ctx.getBean(CategoryService.class);

        Category category = new Category("Test");
        category = categoryService.save(category);
        logger.info("Created category: "+category.toString());

        BigDecimal price = new BigDecimal(100.00).setScale(2, RoundingMode.HALF_UP);

        restTemplate.postForObject(URL_CREATE_PRODUCT_IN_CATEGORY, new Product("Test 1", price), Product.class, category.getId());
        restTemplate.postForObject(URL_CREATE_PRODUCT_IN_CATEGORY, new Product("Test 2", price), Product.class, category.getId());

        logger.info("\n Before delete");
        Products products = restTemplate.getForObject(URL_GET_PRODUCT_BY_CATEGORY, Products.class, category.getId());
        assertNotNull(products);
        List<ProductDTO> productDTOList = products.getProducts();
        printProductsDTO(productDTOList);

        restTemplate.delete(URL_DELETE_PRODUCT_BY_CATEGOTY_ID, category.getId());

        logger.info("\n After delete");
        products = restTemplate.getForObject(URL_GET_PRODUCT_BY_CATEGORY, Products.class, category.getId());
        assertNotNull(products);
        productDTOList = products.getProducts();
        printProductsDTO(productDTOList);

        categoryService.delete(category);
    }

    private void printProductsDTO(List<ProductDTO> products){
        products.forEach(product ->
                logger.info(product.toString()));
    }

}
