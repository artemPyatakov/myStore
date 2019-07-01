package service;

import com.pyatakov.config.DataServiceConfig;
import com.pyatakov.config.TxConfig;
import com.pyatakov.entity.Category;
import com.pyatakov.service.CategoryService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.orm.jpa.JpaSystemException;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CategoryServiceTest {

    final Logger logger =
            LoggerFactory.getLogger(CategoryServiceTest.class);

    private GenericApplicationContext ctx;
    private CategoryService categoryService;

    @Before
    public void setUp(){
        ctx = new AnnotationConfigApplicationContext(DataServiceConfig.class
                                                    ,TxConfig.class);
        categoryService = ctx.getBean(CategoryService.class);
        assertNotNull(categoryService);
    }

    @Test
    public void findByIdTest(){
        Category category = categoryService.findById(1L);
        assertNotNull(category);
        System.out.println(category);
    }

    @Test
    public void findAllTest(){
        List<Category> categories = categoryService.findAll();
        assertNotNull(categories);
        printCategories(categories);
    }

    @Test
    public void saveAndDeleteTest(){
        Category category = new Category("Тест");
        category = categoryService.save(category);

        List<Category> categories = categoryService.findAll();
        printCategories(categories);

        categoryService.delete(category);

        categories = categoryService.findAll();
        printCategories(categories);
    }

    @Test
    public void deleteCategoryHaveProductTest() {
        Category category = categoryService.findById(1L);
        assertNotNull(category);
        boolean flag = false;
        try {
            categoryService.delete(category);
        } catch (JpaSystemException e){
           logger.error("Не возможно удалить категорию, так как к ней привязаны продукты");
           flag = true;
        }
        assertTrue(flag);
    }

    private static void printCategories(List<Category> categories){
        System.out.println("\n");
        for (Category category : categories) {
            System.out.println(category);
        }
        System.out.println("\n");
    }


    @After
    public void tearDown(){
        ctx.close();
    }

}
