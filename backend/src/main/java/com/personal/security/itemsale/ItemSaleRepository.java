package com.personal.security.itemsale;

import com.personal.security.cart.Cart;
import com.personal.security.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemSaleRepository extends JpaRepository<ItemSale, Integer> {

    Optional<List<ItemSale>> findByCartAndItem(Cart cart, Item item);
}
