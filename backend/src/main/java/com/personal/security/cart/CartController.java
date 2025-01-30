package com.personal.security.cart;

import com.personal.security.cart.dto.FinishSaleRequest;
import com.personal.security.cart.dto.ItemSaleRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("addItem")
    public ResponseEntity<Cart> addItem(@RequestBody ItemSaleRequest itemSaleRequest) {
        try {
            Cart cart = cartService.addItemToCart(itemSaleRequest.getUserId(), itemSaleRequest.getItemId(), itemSaleRequest.getQuantity());
            return ResponseEntity.ok(cart);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("removeItem")
    public ResponseEntity<Cart> removeItem(@RequestBody ItemSaleRequest itemSaleRequest) {
        Cart cart = cartService.removeItemFromCart(itemSaleRequest.getUserId(), itemSaleRequest.getItemId());
        return ResponseEntity.ok(cart);
    }

    @PostMapping("finishSale")
    public ResponseEntity<Cart> finishSale(@RequestBody FinishSaleRequest finishSaleRequest) {
        try {
            Cart cart = cartService.finishSale(finishSaleRequest.getUserId(), finishSaleRequest.getAddress());
            return ResponseEntity.ok(cart);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
