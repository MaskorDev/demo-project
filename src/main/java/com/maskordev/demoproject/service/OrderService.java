package com.maskordev.demoproject.service;

import com.maskordev.demoproject.entity.Order;
import com.maskordev.demoproject.entity.OrderEvent;
import com.maskordev.demoproject.exception.OrderNotFoundException;

public interface OrderService {
    void publishEvent(OrderEvent event);

    Order findOrder(int id) throws OrderNotFoundException;
}
