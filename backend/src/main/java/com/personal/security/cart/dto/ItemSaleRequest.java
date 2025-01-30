package com.personal.security.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemSaleRequest {

    private Integer userId;
    private Integer itemId;
    private Integer quantity;
}
