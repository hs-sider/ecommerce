package com.personal.security.cart;

import com.personal.security.item.Item;
import com.personal.security.item.ItemRepository;
import com.personal.security.itemsale.ItemSale;
import com.personal.security.itemsale.ItemSaleRepository;
import com.personal.security.user.User;
import com.personal.security.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CartService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;
    private final ItemSaleRepository itemSaleRepository;

    public Cart addItemToCart(Integer userId, Integer itemId, Integer quantity) throws Exception {

        User user = userRepository.findById(userId).orElseThrow();
        Cart cart = cartRepository.findByStatusAndUser(Status.IN_PROGRESS, user).orElseGet(() -> cartRepository.save(new Cart(user)));
        Item item = itemRepository.findById(itemId).orElseThrow();

        if (item.getStock() < quantity) {
            throw new Exception("Not enough stock of product " + item.getDescription());
        }
        ItemSale itemSale = new ItemSale();
        itemSale.setCart(cart);
        itemSale.setItem(item);
        itemSale.setQuantity(quantity);

        itemSaleRepository.save(itemSale);
        cart.addItemSale(itemSale);

        return cart;
    }

    public Cart removeItemFromCart(Integer userId, Integer itemId) {

        User user = userRepository.findById(userId).orElseThrow();
        Cart cart = cartRepository.findByStatusAndUser(Status.IN_PROGRESS, user).orElseThrow();
        Item item = itemRepository.findById(itemId).orElseThrow();

        List<ItemSale> itemSaleListToRemove = itemSaleRepository.findByCartAndItem(cart, item).orElseThrow();

        itemSaleRepository.deleteAll(itemSaleListToRemove);

        return cart;
    }

    public Cart finishSale(Integer userId, String newAddress) throws Exception {
        User user = userRepository.findById(userId).orElseThrow();
        Cart cart = cartRepository.findByStatusAndUser(Status.IN_PROGRESS, user).orElseThrow();

        for (ItemSale itemSale : cart.getItemSaleList()) {
            if (itemSale.getQuantity() > itemSale.getItem().getStock()) {
                throw new Exception("Not enough stock of product " + itemSale.getItem().getDescription());
            }
            Item item = itemRepository.findById(itemSale.getItem().getId()).orElseThrow();
            item.setStock(item.getStock() - itemSale.getQuantity());
            itemRepository.save(item);
        }

        cart.setAddress(newAddress);
        cart.setStatus(Status.CONFIRMED);
        cartRepository.save(cart);

        return cart;
    }
}
