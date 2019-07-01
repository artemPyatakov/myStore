package service;

import com.pyatakov.config.DataServiceConfig;
import com.pyatakov.config.TxConfig;
import com.pyatakov.entity.Order;
import com.pyatakov.entity.OrderDetail;
import com.pyatakov.other.OrderDTO;
import com.pyatakov.service.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertNotNull;

public class OrderServiceTest {

    final Logger logger =
            LoggerFactory.getLogger(OrderServiceTest.class);

    private GenericApplicationContext ctx;
    private OrderService orderService;

    @Before
    public void setUp() {
        ctx = new AnnotationConfigApplicationContext(DataServiceConfig.class
                ,TxConfig.class);
        orderService = ctx.getBean(OrderService.class);
        assertNotNull(orderService);
    }

    @Test
    public void updateStatusTest(){
      orderService.updateStatus(1L, 3);
    }

    @Test
    public void findOrderForCSVTest() {
        List<OrderDTO> list = orderService.findOrderForCSV();
        assertNotNull(list);
        for (OrderDTO orderDTO : list) {
            System.out.println(orderDTO);
        }

    }

    @Test
    public void findAllTest(){
      List<Order> orders = orderService.findAll();
      assertNotNull(orders);
      orders.forEach(order -> logger.info(order.toString()));
    }

    @Test
    public void saveTest() {
        System.out.println("\n");
        List<Order> orders = orderService.findAll();
        assertNotNull(orders);
        assertNotNull(orders.get(0));
        System.out.println("\n");

        Order order = orders.get(0);
        System.out.println("\n "+order);
        order.setFirstName("Artem");
        order.setLastName("Pyatakov 1");

        Set<OrderDetail> details
                = order.getOrderDetails();

        OrderDetail orderDetail
                = details.stream().filter(d -> d.getId_product() == 1)
                         .findFirst().get();
        orderDetail.setAmount(44);
        orderDetail.setSumma(new BigDecimal(120001.00));

        OrderDetail orderDetailCreate
                = new OrderDetail(3, 2, new BigDecimal(23.00), order);
        details.add(orderDetailCreate);
        order = orderService.save(order);
        details = order.getOrderDetails();
        System.out.println(order);

        OrderDetail orderDetailDelete = details.stream().filter(d -> d.getId_product() == 3)
                .findFirst().get();
        System.out.println("\n Product with id = 3: " + orderDetailDelete);

        details.remove(orderDetailDelete);
        orderService.save(order);
        orders = orderService.findAll();
        orders.forEach(o -> logger.info(o.toString()));
    }


}
