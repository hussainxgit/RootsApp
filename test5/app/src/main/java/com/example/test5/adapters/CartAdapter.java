package com.example.test5.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test5.cartDatabase;
import com.example.test5.models.cartItem;
import com.example.test5.pages.ProductPage;
import com.example.test5.R;
import com.example.test5.models.Product;
import com.example.test5.pages.ShoppingCart;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter {
    private double cartItemsPrice;
    List<cartItem> cartItems;
    Context context;

    public CartAdapter(List<cartItem> cartItems, Context context) {
        this.cartItems = cartItems;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_list, parent, false);
        return new ItemViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        final cartDatabase appDP = cartDatabase.getInstance(context);
        // Update cart items price
        cartItemsPrice = cartItemsPrice+cartItems.get(position).getProductPrice();
        ((ShoppingCart)context).setCartTotalPrice(cartItemsPrice);
        Log.e(context+"", cartItemsPrice+"");

        itemViewHolder.productName.setText(cartItems.get(position).getProductName());
        itemViewHolder.productPrice.setText(cartItems.get(position).getProductPrice() + "");
        itemViewHolder.productQuantity.setText(cartItems.get(position).getProductQuantity() + "");
        itemViewHolder.cartItemDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final cartItem cartItemRedo = new cartItem(cartItems.get(position).getProductName(), cartItems.get(position).getProductPrice(), cartItems.get(position).getProductQuantity());
                if (cartItems.size() != 0) {
                    cartItemsPrice = cartItemsPrice-cartItems.get(position).getProductPrice();
                    ((ShoppingCart)context).setCartTotalPrice(cartItemsPrice);
                    cartItems.remove(position);
                    appDP.cartDao().deleteCartItem(cartItemRedo.getProductName());
                    notifyDataSetChanged();
                }
                Snackbar snackbar = Snackbar
                        .make(view, "Wow you deleted item", Snackbar.LENGTH_LONG)
                        .setAction("Redo", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                cartItems.add(cartItemRedo);
                                appDP.cartDao().insertCartItem(cartItemRedo);
                                Log.e(context+"", cartItemsPrice+"");
                                cartItemsPrice = cartItemsPrice+cartItems.get(position).getProductPrice();
                                ((ShoppingCart)context).setCartTotalPrice(cartItemsPrice);
                                notifyDataSetChanged();
                            }
                        });
                snackbar.show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return cartItems.size();
    }
    private static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView productName;
        TextView productPrice;
        EditText productQuantity;
        ImageButton cartItemDelete;
        RelativeLayout cartLayout;

        public ItemViewHolder(View itemView) {
            super(itemView);
            productName = (TextView) itemView.findViewById(R.id.productName);
            productPrice = (TextView) itemView.findViewById(R.id.productPrice);
            productQuantity = (EditText) itemView.findViewById(R.id.productQuantity);
            cartItemDelete = (ImageButton) itemView.findViewById(R.id.cartItemDelete);
            cartLayout = (RelativeLayout) itemView.findViewById(R.id.cartLayout);
        }
    }
}
