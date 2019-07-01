package com.pyatakov.controller;


import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.pyatakov.component.Basket;
import com.pyatakov.entity.Order;
import com.pyatakov.entity.OrderDetail;
import com.pyatakov.other.OrderDTO;
import com.pyatakov.other.UserData;
import com.pyatakov.other.objectContainer.Orders;
import com.pyatakov.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/order")
public class OrderController {

    private Logger logger =
            LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private Basket basket;

    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/private/listData")
    public Orders listData() {
        return new Orders(orderService.findAll());
    }

    @PutMapping(value = "/private/changeStatus/{id}")
    public void changeStatus(@PathVariable Long id
                            ,@RequestParam int id_status) {
        logger.info("Update orderStatus. id_order = " + id + ", id_status = " + id_status);
        orderService.updateStatus(id, id_status);
    }

    @GetMapping(value = "/private/statusList")
    public Map<String,Integer> statusList() {
        Map<String, Integer> map = new HashMap<>();
        map.put("NEW", 1);
        map.put("IN_PROGRESS", 2);
        map.put("COMPLETE", 3);
        return map;
    }

    @PostMapping("/public/create")
    public Order create(@RequestBody UserData userData, HttpSession httpSession) {
         httpSession.setMaxInactiveInterval(3600*24);
         if (basket.getProductIdList().size()>0) {
             logger.info("Create order");
             Order order = new Order(1, userData.getFirstName(), userData.getLastName(), userData.getPhone());
             for (Basket.ProductsOrder productsOrder : basket.getProductIdList()) {
                 order.getOrderDetails().add(new OrderDetail(
                         productsOrder.getId_product(),
                         productsOrder.getAmount(),
                         productsOrder.getSumma(),
                         order
                 ));
             }
             basket.getProductIdList().clear();
             return orderService.save(order);
         } else
             logger.info("Basket is empty");
         return null;
    }

    @GetMapping(value = "/private/downloadCompliteOrders", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody void download(HttpServletResponse response) throws IOException {
        List<OrderDTO> orders = orderService.findOrderForCSV();
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = CsvSchema.builder()
                .addColumn("id")
                .addColumn("firstName")
                .addColumn("lastName")
                .addColumn("phone")
                .addColumn("summa")
                .build();
        ObjectWriter writer = mapper.writer(schema.withLineSeparator("\n"));
        File file = File.createTempFile("orders",".csv", null);
        writer.writeValue(file, orders);

        InputStream in = new FileInputStream(file);
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
        response.setHeader("Content-Length", String.valueOf(file.length()));
        FileCopyUtils.copy(in, response.getOutputStream());

        if (file.exists()) file.delete();
    }

}
