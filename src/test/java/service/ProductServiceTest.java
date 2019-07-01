package service;

import com.pyatakov.config.DataServiceConfig;
import com.pyatakov.config.TxConfig;
import com.pyatakov.entity.Category;
import com.pyatakov.entity.Product;
import com.pyatakov.other.objectContainer.product.ProductDTO;
import com.pyatakov.service.CategoryService;
import com.pyatakov.service.ProductService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class ProductServiceTest {
    private GenericApplicationContext ctx;
    private ProductService productService;

    @Before
    public void setUp() {
        ctx = new AnnotationConfigApplicationContext(DataServiceConfig.class
                ,TxConfig.class);
        productService = ctx.getBean(ProductService.class);
        assertNotNull(productService);
    }

    @Test
    public void findProductsByCategory_IdTest() {
        long id = 1;
        List<Product> products = productService.findProductsByCategory_Id(id);
        assertNotNull(products);
        for (Product product : products) {
            System.out.println(product);
        }
    }

    @Test
    public void findProductsByCategory_Id_DtoTest() {
        long id = 1;
        List<ProductDTO> products = productService.findProductsByCategory_Id_Dto(id);
        assertNotNull(products);
        for (ProductDTO product : products) {
            System.out.println(product);
        }
    }

    @Test
    public void findByIdTest() {
        long id = 1;
        Product product = productService.findById(id);
        assertNotNull(product);
        System.out.println(product);
    }

    @Test
    public void findInfoById() {
        long id = 1;
        ProductDTO product = productService.findInfoById(id);
        assertNotNull(product);
        System.out.println(product);
    }

    @Test
    public void deleteAllTest() {
         CategoryService categoryService = ctx.getBean(CategoryService.class);
         assertNotNull(categoryService);
         long id = 1;
         Category category = categoryService.findById(id);

         List<Product> productsInsert = new ArrayList<>();
         productsInsert.add(new Product("Test 1", new BigDecimal(100.10),"Test info 1", category));
         productsInsert.add(new Product("Test 2", new BigDecimal(100.10),"Test info 1", category));

        for (Product product : productsInsert) {
            productService.save(product);
        }
        System.out.println("\nBefore delete");
        List<Product> products = productService.findProductsByCategory_Id(id);
        assertNotNull(products);
        List<Product> productsDelete = new ArrayList<>();
        for (Product product : products) {
            System.out.println(product);
            if (product.getName().lastIndexOf("Test")!=-1)
                productsDelete.add(product);
        }
        System.out.println("\nProducts for delete:");
        for (Product product : productsDelete) {
            System.out.println(product);
        }
        productService.deleteAll(productsDelete);                              

        System.out.println("\nAfter delete");
        products = productService.findProductsByCategory_Id(id);
        assertNotNull(products);
        for (Product product : products) {
            System.out.println(product);
        }

    }

    @Test
    public void saveAndDeleteTest() {
        CategoryService categoryService = ctx.getBean(CategoryService.class);
        assertNotNull(categoryService);

        long id_category = 1;

        //Создание нового продукта
        Category category = categoryService.findById(id_category);
        Product product = new Product("Test",new BigDecimal(100.00),category);
        product.setInfo("Info for test product");

        product = productService.save(product);
        System.out.println("\n"+product);
        //

        //Обновление данных о продукте
        product.setPrice(new BigDecimal(150.50));
        product.setInfo("Test update product");

        product = productService.save(product);
        System.out.println("\n"+product);
        //

        System.out.println("\n");
        List<Product> products = productService.findProductsByCategory_Id(id_category);
        assertNotNull(products);
        for (Product p : products) {
            System.out.println(p);
        }

        //удаление продукта
        productService.delete(product);

        System.out.println("\n");
        products = productService.findProductsByCategory_Id(id_category);
        assertNotNull(products);
        for (Product p : products) {
            System.out.println(p);
        }
    }

    @After
    public void tearDown() {
        ctx.close();
    }
}
