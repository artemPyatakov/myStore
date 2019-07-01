package controller;


import com.pyatakov.component.Basket;
import com.pyatakov.other.UserData;
import com.pyatakov.entity.Order;
import restClient.RestClientConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RestClientConfig.class})
public class BasketAndOrderControllersTest {

    final Logger logger =
            LoggerFactory.getLogger(CategoryRestControllerTest.class);

    private static final String URL_PUT_IN_BASKET =
            "http://localhost:8080/basket/public/put";

    private static final String URL_GET_BASKET =
            "http://localhost:8080/basket/public/get";    

    private static final String URL_CLEAR_BASKET =
            "http://localhost:8080/basket/public/clear";


    private static final String URL_CREATE_ORDER =
            "http://localhost:8080/order/public/create";

    @Autowired
    RestTemplate restTemplate;

    @Before
    public void setUp() {
        assertNotNull(restTemplate);
    }

    @Test
    public void createOrder() {
        String uid = UUID.randomUUID().toString();

        List<Basket.ProductsOrder> list
                = Arrays.asList(
                new Basket.ProductsOrder(1,2, new BigDecimal(23.00)),
                new Basket.ProductsOrder(2,2, new BigDecimal(50.00))
        );

        System.out.println("\nКладём в карзину значения:");
        list.forEach(e -> logger.info(e.toString()));
        requestWithSession(restTemplate, HttpMethod.PUT, list, uid, URL_PUT_IN_BASKET, String.class);

        System.out.println("\nДелаем заказ:");
        UserData userData = new UserData("Artem", "Pyatakov", "33-55-88");
        requestWithSession(restTemplate, HttpMethod.POST, userData, uid, URL_CREATE_ORDER, Order.class);

        logger.info("\n Проверяем очистилась ли карзина:");
        requestWithSession(restTemplate, HttpMethod.GET, null, uid, URL_GET_BASKET, List.class);
    }

    @Test
    public void putAndGetBasket() {
        String uid = UUID.randomUUID().toString();
        
        List<Basket.ProductsOrder> list
                = Arrays.asList(
                        new Basket.ProductsOrder(1,2, new BigDecimal(23.00)),
                        new Basket.ProductsOrder(2,2, new BigDecimal(50.00))
                );

        logger.info("\n Кладём в карзину значения");
        list.forEach(e -> logger.info(e.toString()));
        requestWithSession(restTemplate, HttpMethod.PUT, list, uid, URL_PUT_IN_BASKET, String.class);

        logger.info("\n Получаем значения из карзины");
        requestWithSession(restTemplate, HttpMethod.GET, null, uid, URL_GET_BASKET, List.class);

        logger.info("\n Очищаем карзину");
        requestWithSession(restTemplate, HttpMethod.PUT, null, uid, URL_CLEAR_BASKET, String.class);

        logger.info("\n Получаем значения из карзины");
        requestWithSession(restTemplate, HttpMethod.GET, null, uid, URL_GET_BASKET, List.class);
    }

    private <T> void requestWithSession (
            RestTemplate restTemplate, HttpMethod httpMethod, T body, String sessionId, String uri, Class resultType
    ) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "JSESSIONID=" + sessionId);
        HttpEntity requestEntity = new HttpEntity(body, requestHeaders);
        ResponseEntity responseEntity =
                restTemplate.exchange(uri, httpMethod,
                        requestEntity, resultType);
        logger.info(Optional.ofNullable(responseEntity.getBody()).orElse("").toString());
    }

}
