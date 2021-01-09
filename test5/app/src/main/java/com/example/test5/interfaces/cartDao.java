package com.example.test5.interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.test5.models.cartItem;

import java.util.List;

@Dao
public interface cartDao {
    @Query("SELECT * FROM cartItem ORDER BY id")
    List<cartItem> getCartItems();

    @Insert
    void insertCartItem(cartItem cartItem);

    @Query("UPDATE cartItem SET productQuantity = :productQuantity WHERE id = :updatedProduct")
    void updateCartItem(int productQuantity, int updatedProduct);

    @Query("DELETE FROM cartItem WHERE id = :id")
    void deleteCartItem(int id);

    @Query("SELECT sum(productPrice) FROM cartItem")
    double getTotalPrice();
}
