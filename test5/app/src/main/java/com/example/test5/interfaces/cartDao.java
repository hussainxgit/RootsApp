package com.example.test5.interfaces;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.test5.models.cartItem;

import java.util.List;

@Dao
public interface cartDao {
    @Query("SELECT * FROM cartItem ORDER BY id")
    List<cartItem> getCartItems();

    @Insert
    void insertCartItem(cartItem cartItem);

    @Update
    void updateCartItem(cartItem carItem);

    @Query("DELETE FROM cartItem WHERE productName = :productName")
    void deleteCartItem(String productName);

    @Query("SELECT sum(productPrice) FROM cartItem")
    double getTotalPrice();
}
