package com.maskordev.demoproject.dto;

import com.maskordev.demoproject.entity.OrderEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class RegisterEventDTO extends BaseEventDTO {
    private int clientId;
    private LocalDateTime expectedDeliveryTime;
    private int productId;
    private double productCost;
}
