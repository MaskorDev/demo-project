package com.maskordev.demoproject.entity;

import com.maskordev.demoproject.enums.OrderStatus;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.aspectj.weaver.ast.Or;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Builder
public class OrderEvent{
    private int orderId;
    private int clientId;
    private int employerId;
    private LocalDateTime expectedDeliveryTime;
    private int productId;
    private String status;
    private double productCost;
    private LocalDateTime dateTime;
    private String details;


    public Order toOrder(OrderEvent order) {
        return Order.builder()
                .orderId(order.getOrderId())
                .clientId(order.getClientId())
                .dateTime(order.getDateTime())
                .employerId(order.getEmployerId())
                .expectedDeliveryTime(order.getExpectedDeliveryTime())
                .productId(order.getProductId())
                .productCost(order.getProductCost())
                .status(order.getStatus())
                .details(order.getDetails())
                .build();
    }

    public static OrderEvent toEvent(Order order) {
        return OrderEvent.builder()
                .orderId(order.getOrderId())
                .clientId(order.getClientId())
                .dateTime(order.getDateTime())
                .employerId(order.getEmployerId())
                .expectedDeliveryTime(order.getExpectedDeliveryTime())
                .productId(order.getProductId())
                .productCost(order.getProductCost())
                .status(order.getStatus())
                .details(order.getDetails())
                .build();
    }

    public static OrderEvent checkStatus(OrderEvent event) {
        if (Objects.equals(event.getStatus(), OrderStatus.ORDER_IS_DELIVERED.name())
                || Objects.equals(event.getStatus(), OrderStatus.ORDER_IS_CANCELED.name())) {
            return event = null;
        }
        return event;
    }

}
