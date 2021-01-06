package com.example.test5.pages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.test5.R;
import com.example.test5.cartDatabase;
import com.example.test5.interfaces.cartDao;
import com.example.test5.models.Product;
import com.example.test5.models.cartItem;
import com.squareup.picasso.Picasso;

public class ProductPage extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String baseUrl = "http://10.0.2.2:5000/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView productName = findViewById(R.id.productName);
        final TextView productPrice = findViewById(R.id.productPrice);
        final TextView productDescription = findViewById(R.id.productDescription);
        final ImageView productImage = findViewById(R.id.productImage);
        final Button addCartItemBtn = findViewById(R.id.addCartItemBtn);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String productNameExtra = intent.getStringExtra("productName");
        String productpriceExtra = intent.getStringExtra("productPrice");
        String productDescriptionExtra = intent.getStringExtra("productDescription");
        String productImageExtra = intent.getStringExtra("productImage");

        productName.setText(productNameExtra);
        productPrice.setText("$" + productpriceExtra);
        productDescription.setText(productDescriptionExtra);

        Picasso.get()
                .load(productImageExtra)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(productImage);

        final cartItem cartItem = new cartItem(productNameExtra, Double.parseDouble(productpriceExtra), 1);

        addCartItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartDatabase appDP = cartDatabase.getInstance(ProductPage.this);
                appDP.cartDao().insertCartItem(cartItem);
                Toast.makeText(ProductPage.this, "Item added", Toast.LENGTH_SHORT).show();
            }
        });
    }
}