package com.example.test5.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.test5.models.Product;
import com.example.test5.adapters.ProductAdapter;
import com.example.test5.interfaces.ProductsApi;
import com.example.test5.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class outDoor extends Fragment {

    private static final String TAG = "MainActivity";
    private static final String baseUrl = "http://10.0.2.2:5000/";
    private ArrayList<Product> productsList;
    private static Button testBtn;
    private ProductAdapter pa;
    private View view;

    //todo: comment this if you want to use the local api
//    private ArrayList<Product> productsLocal = new ArrayList<>();
//    // Fake Data
//    Product prdct1 = new Product(1, "Spider plant", "img", "Hello world", 1.5);
//    Product prdct2 = new Product(2, "Yousif", "img", "Hello world", 2.5);
//    Product prdct3 = new Product(2, "Yousif", "img", "Hello world", 2.5);
//    Product prdct4 = new Product(2, "Yousif", "img", "Hello world", 2.5);

    public outDoor() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_out_door, container, false);
        // Inflate the layout for this fragment

        //todo: comment this if you want to use the local api
//        productsLocal.add(prdct1);
//        productsLocal.add(prdct2);
//        productsLocal.add(prdct3);
//        productsLocal.add(prdct4);
//
//        // Lookup the recyclerview in activity layout
//        RecyclerView productsListView = (RecyclerView) view.findViewById(R.id.rv);
//
//        // Attach the adapter to the recyclerview to populate items
//        productsListView.setHasFixedSize(true);
//
//        // Set layout manager to position the items
//        productsListView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
//
//        // That's all! :(
//        ProductAdapter pa = new ProductAdapter(productsLocal, getActivity());
//        productsListView.setAdapter(pa);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        ProductsApi client = retrofit.create(ProductsApi.class);

        Call<ArrayList<Product>> call = client.getOutdoorProducts();
        call.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                productsList = response.body();

                // Lookup the recyclerview in activity layout
                RecyclerView productsListView = (RecyclerView) view.findViewById(R.id.rv);

                // Attach the adapter to the recyclerview to populate items
                productsListView.setHasFixedSize(true);

                // Set layout manager to position the items
                productsListView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

                // That's all! :(
                pa = new ProductAdapter(productsList, getActivity());
                productsListView.setAdapter(pa);

            }
            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                Log.e(TAG, t.getMessage().toString());
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}