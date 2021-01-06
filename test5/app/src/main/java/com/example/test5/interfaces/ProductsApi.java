package com.example.test5.interfaces;

import com.example.test5.models.Product;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductsApi {

    @GET("/product")
    Call<Product> getProduct();

    @GET("/products/indoor")
    Call<ArrayList<Product>> getIndoorProducts();

    @GET("/products/outdoor")
    Call<ArrayList<Product>> getOutdoorProducts();

    @GET("/products/garden")
    Call<ArrayList<Product>> getGardenProducts();

}
