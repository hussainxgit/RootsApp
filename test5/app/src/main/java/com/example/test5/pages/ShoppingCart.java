package com.example.test5.pages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.test5.R;
import com.example.test5.adapters.CartAdapter;
import com.example.test5.adapters.ProductAdapter;
import com.example.test5.cartDatabase;
import com.example.test5.models.cartItem;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart extends AppCompatActivity {
    private static List<cartItem> cartItems;
    private static RecyclerView productsListView;
    private TextView cartItemsPrice;
    private CartAdapter ca;
    double cartTotalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart2);

        cartDatabase appDP = cartDatabase.getInstance(ShoppingCart.this);
        cartItems = appDP.cartDao().getCartItems();
//        cartTotalPrice = appDP.cartDao().getTotalPrice();

        productsListView = findViewById(R.id.rv_cart);

        // Attach the adapter to the recyclerview to populate items
        productsListView.setHasFixedSize(true);

        // Set layout manager to position the items
        productsListView.setLayoutManager(new LinearLayoutManager(ShoppingCart.this));

        // That's all! :(
        ca = new CartAdapter(cartItems, ShoppingCart.this, cartTotalPrice);
        productsListView.setAdapter(ca);

        // Calculate all cart items
        cartItemsPrice = findViewById(R.id.cartTotalItemsPrice);
        cartItemsPrice.setText("$" + cartTotalPrice + "");

    }

    public void setCartTotalPrice(double cartTotalPrice) {
        this.cartTotalPrice = cartTotalPrice;
        cartItemsPrice.setText("$" + cartTotalPrice + "");
    }

}