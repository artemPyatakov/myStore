package controller;

import com.pyatakov.entity.Category;
import com.pyatakov.other.objectContainer.Categories;
import restClient.RestClientConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RestClientConfig.class})
public class CategoryRestControllerTest {

    final Logger logger =
            LoggerFactory.getLogger(CategoryRestControllerTest.class);

    private static final String URL_GET_ALL_CATEGORIES =
            "http://localhost:8080/category/public/listData";
    private static final String URL_DELETE_CATEGORY_BY_ID =
            "http://localhost:8080/category/private/delete/{id}";
    private static final String URL_CREATE_CATEGORY =
            "http://localhost:8080/category/private/create";
    private static final String URL_UPDATE_CATEGORY =
            "http://localhost:8080/category/private/update";


    @Autowired
    RestTemplate restTemplate;

    @Before
    public void setUp(){
      assertNotNull(restTemplate);
    }

    @Test
    public void listDataTest() {
        logger.info("\nGet all categories (test):");
        Categories categories = restTemplate.getForObject(URL_GET_ALL_CATEGORIES, Categories.class);
        assertNotNull(categories);
        printCategories(categories.getCategories());
    }

    @Test
    public void updateTest(){
        // Меняем наименование категории с id = 1
        Categories categories = restTemplate.getForObject(URL_GET_ALL_CATEGORIES, Categories.class);
        assertNotNull(categories);
        Category category = categories.getCategories().get(0);
        category.setName("Смартфоны и гаджеты 1");
        logger.info("\n"+category);
        //Обновляем
        restTemplate.put(URL_UPDATE_CATEGORY, category);

        categories = restTemplate.getForObject(URL_GET_ALL_CATEGORIES, Categories.class);
        assertNotNull(categories);
        printCategories(categories.getCategories());
        //

        // Возвращаем на место
        categories = restTemplate.getForObject(URL_GET_ALL_CATEGORIES, Categories.class);
        assertNotNull(categories);
        category = categories.getCategories().get(0);
        category.setName("Смартфоны и гаджеты");
        //Обновляем
        restTemplate.put(URL_UPDATE_CATEGORY, category);
        
        categories = restTemplate.getForObject(URL_GET_ALL_CATEGORIES, Categories.class);
        assertNotNull(categories);
        printCategories(categories.getCategories());
        //

        //Попытка обновить категорию с несуществующим id
        category.setName("Смартфоны и гаджеты 1");
        category.setId(1000L);
        restTemplate.put(URL_UPDATE_CATEGORY, category);

        //Попытка создать категорию через метод обновления
        category = new Category("Test");
        restTemplate.put(URL_UPDATE_CATEGORY, category);

        categories = restTemplate.getForObject(URL_GET_ALL_CATEGORIES, Categories.class);
        assertNotNull(categories);
        printCategories(categories.getCategories());
    }

    @Test
    public void validatorTest() {
        logger.info("\nValidator category test:");
        Category category = new Category("Te");
        category = restTemplate.postForObject(URL_CREATE_CATEGORY, category, Category.class);
        assertNull(category);

        category = new Category(null);
        category = restTemplate.postForObject(URL_CREATE_CATEGORY, category, Category.class);
        assertNull(category);
    }


    @Test
    public void createAndDeleteTest() {
        logger.info("\nTest create category:");
        Category category = new Category("Test");
        category = restTemplate.postForObject(URL_CREATE_CATEGORY, category, Category.class);
        assertNotNull(category);
        logger.info("Category was created :" + category);

        Categories categories = restTemplate.getForObject(URL_GET_ALL_CATEGORIES, Categories.class);
        assertNotNull(categories);
        printCategories(categories.getCategories());

        logger.info("Test delete category");
        restTemplate.delete(URL_DELETE_CATEGORY_BY_ID, category.getId());

        categories = restTemplate.getForObject(URL_GET_ALL_CATEGORIES, Categories.class);
        assertNotNull(categories);

        printCategories(categories.getCategories());
        logger.info("Delete successfully");
    }


    private void printCategories(List<Category> categories){
        System.out.println("\n");
        for (Category category : categories) {
            logger.info(category.toString());
        }
        logger.info("\n");
    }

}
