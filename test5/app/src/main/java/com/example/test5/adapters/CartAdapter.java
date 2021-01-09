package com.example.test5.adapters;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
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

    private static final String TAG = "MyActivity";

    public CartAdapter(List<cartItem> cartItems, Context context, double cartItemsPrice) {
        this.cartItems = cartItems;
        this.context = context;
        this.cartItemsPrice = cartItemsPrice;
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

        if (cartItems.get(position).getProductQuantity() >= 1) {
            for (int i = 1; i <= cartItems.get(position).getProductQuantity(); i++) {
                ((ShoppingCart) context).setCartTotalPrice(cartItemsPrice += cartItems.get(position).getProductPrice());
            }
        } else {
            ((ShoppingCart) context).setCartTotalPrice(cartItemsPrice += cartItems.get(position).getProductPrice());
        }

        Picasso.get()
                .load(cartItems.get(position).getProductImg())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(itemViewHolder.productImg);

        itemViewHolder.productName.setText(cartItems.get(position).getProductName());
        itemViewHolder.productPrice.setText(cartItems.get(position).getProductPrice() + "");
        itemViewHolder.productQuantity.setText(cartItems.get(position).getProductQuantity() + "");
        itemViewHolder.cartItemDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final cartItem cartItemRedo = new cartItem(cartItems.get(position).getProductName(),
                        cartItems.get(position).getProductPrice(),
                        cartItems.get(position).getProductQuantity(),
                        cartItems.get(position).getProductImg());

                if (cartItems.size() != 0) {
                    Log.e(TAG, cartItemRedo.getId()+" - "+cartItems.get(position).getId());
                    cartItemsPrice = cartItemsPrice - cartItems.get(position).getProductPrice();
                    ((ShoppingCart) context).setCartTotalPrice(cartItemsPrice);
                    cartItems.remove(position);
                    appDP.cartDao().deleteCartItem(cartItems.get(position).getId());
                    notifyDataSetChanged();
                }

                Snackbar snackbar = Snackbar
                        .make(view, "Wow you deleted item", Snackbar.LENGTH_LONG)
                        .setAction("Redo", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                cartItemsPrice = cartItemsPrice + cartItemRedo.getProductPrice();
                                cartItems.add(cartItemRedo);
                                appDP.cartDao().insertCartItem(cartItemRedo);
                                ((ShoppingCart) context).setCartTotalPrice(cartItemsPrice);
                                notifyDataSetChanged();
                            }
                        });
                snackbar.show();
            }
        });
        itemViewHolder.increaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appDP.cartDao().updateCartItem(cartItems.get(position).getProductQuantity() + 1, cartItems.get(position).getId());
                cartItems.get(position).setProductQuantity(cartItems.get(position).getProductQuantity() + 1);
                itemViewHolder.productQuantity.setText(cartItems.get(position).getProductQuantity() + "");

                cartItemsPrice = cartItemsPrice + cartItems.get(position).getProductPrice();
                ((ShoppingCart) context).setCartTotalPrice(cartItemsPrice);
            }
        });
        itemViewHolder.decreaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cartItems.get(position).getProductQuantity() > 1) {
                    appDP.cartDao().updateCartItem(cartItems.get(position).getProductQuantity() - 1, cartItems.get(position).getId());
                    cartItems.get(position).setProductQuantity(cartItems.get(position).getProductQuantity() - 1);
                    itemViewHolder.productQuantity.setText(cartItems.get(position).getProductQuantity() + "");

                    cartItemsPrice = cartItemsPrice - cartItems.get(position).getProductPrice();
                    ((ShoppingCart) context).setCartTotalPrice(cartItemsPrice);

                }
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
        ImageView productImg;
        EditText productQuantity;
        ImageButton cartItemDelete;
        RelativeLayout cartLayout;
        ImageButton increaseBtn;
        ImageButton decreaseBtn;

        public ItemViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productQuantity = itemView.findViewById(R.id.productQuantity);
            cartItemDelete = itemView.findViewById(R.id.cartItemDelete);
            cartLayout = itemView.findViewById(R.id.cartLayout);
            productImg = itemView.findViewById(R.id.productImg);
            increaseBtn = itemView.findViewById(R.id.increaseBtn);
            decreaseBtn = itemView.findViewById(R.id.decreaseBtn);
        }
    }
}
