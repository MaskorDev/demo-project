package com.maskordev.demoproject.controller;

import com.maskordev.demoproject.dto.*;
import com.maskordev.demoproject.entity.Order;
import com.maskordev.demoproject.entity.OrderEvent;
import com.maskordev.demoproject.enums.OrderStatus;
import com.maskordev.demoproject.exception.FirstRegisterEventException;
import com.maskordev.demoproject.exception.OrderNotFoundException;
import com.maskordev.demoproject.repository.OrderRepository;
import com.maskordev.demoproject.service.OrderService;
import com.maskordev.demoproject.service.OrderServiceImpl;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final OrderRepository orderRepository;

    private OrderEvent event;

    public OrderController(OrderService orderService, OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        this.orderService = new OrderServiceImpl(orderRepository);
    }


    @GetMapping("/")
    public String ok() {
        return "ok";
    }

    @PostMapping("/register")
    public OrderEvent registerEvent(@RequestBody RegisterEventDTO register) {
        Random r = new Random();


        Order order = Order.builder()
                .orderId(r.nextInt(1, 1000))
                .clientId(r.nextInt(1, 1000))
                .employerId(r.nextInt(1, 1000))
                .expectedDeliveryTime(LocalDateTime.now().plusMinutes(10))
                .productId(register.getProductId())
                .productCost((double) Math.round(r.nextDouble(200, 1000) * 100) / 100)
                .dateTime(LocalDateTime.now())
                .status(OrderStatus.ORDER_IS_REGISTERED.name())
                .build();

        System.out.println(order.getProductCost());

        event = OrderEvent.toEvent(order);

        orderService.publishEvent(event);

        return event;
    }

    @GetMapping("/ready")
    public OrderEvent readyEvent() throws FirstRegisterEventException {
        event.setDateTime(LocalDateTime.now());

        event = OrderEvent.checkStatus(event);

        event.setStatus(OrderStatus.ORDER_IS_READY_TO_DELIVERY.name());

        orderService.publishEvent(event);

        return event;
    }

    @GetMapping("/progress")
    public OrderEvent progressEvent() {
        event.setDateTime(LocalDateTime.now());

        event = OrderEvent.checkStatus(event);

        event.setStatus(OrderStatus.ORDER_IS_PROCESSED.name());

        orderService.publishEvent(event);

        return event;
    }

    @GetMapping("/complete")
    public OrderEvent completeEvent() {
        event.setDateTime(LocalDateTime.now());

        event = OrderEvent.checkStatus(event);

        event.setStatus(OrderStatus.ORDER_IS_DELIVERED.name());

        orderService.publishEvent(event);

        return event;
    }

    @PostMapping("/cancel")
    public OrderEvent cancelEvent(@RequestBody CancelEventDTO cancel) {
        event.setDateTime(LocalDateTime.now());

        event = OrderEvent.checkStatus(event);

        event.setStatus(OrderStatus.ORDER_IS_CANCELED.name());
        event.setDetails(cancel.getDetails());

        orderService.publishEvent(event);

        return event;
    }

    @GetMapping("/{id}")
    public Order findOrder(@PathVariable Integer id) throws OrderNotFoundException {
        return orderService.findOrder(id);
    }

    @GetMapping("/events")
    public List<OrderEvent> findAllEventsByOrderId(
            @RequestParam Integer orderId,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize

    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        ExampleMatcher matcher = ExampleMatcher.matchingAny()
                .withMatcher("orderId", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

        Example<Order> example = Example.of(
                Order.builder()
                        .orderId(orderId)
                        .build(), matcher
        );

        return orderRepository.findAll(example, pageable).stream().map(OrderEvent::toEvent).collect(Collectors.toList());
    }
}
