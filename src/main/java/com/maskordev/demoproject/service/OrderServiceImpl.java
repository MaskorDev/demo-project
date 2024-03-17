package com.maskordev.demoproject.service;

import com.maskordev.demoproject.entity.Order;
import com.maskordev.demoproject.entity.OrderEvent;
import com.maskordev.demoproject.exception.OrderNotFoundException;
import com.maskordev.demoproject.repository.OrderRepository;
import org.antlr.v4.runtime.atn.SemanticContext;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;

@Service
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @Override
    public void publishEvent(OrderEvent event) {
        Order order = event.toOrder(event);
        orderRepository.save(order);
    }

    @Override
    public Order findOrder(int id) throws OrderNotFoundException {
        return orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
    }

}
