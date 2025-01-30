package com.personal.security.cart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.personal.security.itemsale.ItemSale;
import com.personal.security.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cart {

    public Cart(User user) {
        this.user = user;
        this.address = user.getAddress();
        this.status = Status.IN_PROGRESS;
        this.itemSaleList = new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer orderNumber;

    private String address;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemSale> itemSaleList;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void addItemSale(ItemSale itemSale) {
        this.itemSaleList.add(itemSale);
    }
}
