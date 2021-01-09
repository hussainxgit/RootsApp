package com.example.test5.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test5.pages.ProductPage;
import com.example.test5.R;
import com.example.test5.models.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter {
    List<Product> productArray;
    Context context;
    String imgUrl;

    public ProductAdapter(ArrayList<Product> productArray, Context context) {
        this.productArray = productArray;
        this.context = context;
    }

    @NonNull
    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //Inflating recycle view item layout
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_list_item, parent, false);
            return new ItemViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //todo: fix position
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            imgUrl = "http://10.0.2.2:5000/static"+productArray.get(position).getProductImage();
            Picasso.get()
                    .load(imgUrl)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(itemViewHolder.productImage);

            itemViewHolder.productName.setText(productArray.get(position).getProductName());
            itemViewHolder.productPrice.setText("$"+productArray.get(position).getProductPrice() + "");
            itemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ProductPage.class);
                    intent.putExtra("productName", productArray.get(position).getProductName());
                    intent.putExtra("productPrice", productArray.get(position).getProductPrice() + "");
                    intent.putExtra("productDescription", productArray.get(position).getProductDescription());
                    intent.putExtra("productImage", "http://10.0.2.2:5000/static"+productArray.get(position).getProductImage());
                    context.startActivity(intent);
                }
            });
    }

    @Override
    public int getItemCount() {
        return productArray.size();
    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView productName;
        TextView productDescription;
        TextView productPrice;
        ImageView productImage;

        public ItemViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productDescription = itemView.findViewById(R.id.productDescription);
            productImage = itemView.findViewById(R.id.productImage);
        }
    }
}
