package com.maskordev.demoproject.entity;

import com.maskordev.demoproject.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int id;
    protected int orderId;
    @Column(name = "client_id")
    protected int clientId;
    protected int employerId;
    protected LocalDateTime expectedDeliveryTime;
    protected int productId;
    protected String status;
    protected double productCost;
    protected LocalDateTime dateTime;
    protected String details;
}
