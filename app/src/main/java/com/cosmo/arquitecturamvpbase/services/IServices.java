package com.cosmo.arquitecturamvpbase.services;

import com.cosmo.arquitecturamvpbase.model.Customer;
import com.cosmo.arquitecturamvpbase.model.DeleteResponse;
import com.cosmo.arquitecturamvpbase.model.Product;
import com.cosmo.arquitecturamvpbase.model.User;

import java.util.ArrayList;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by leidyzulu on 16/09/17.
 */

public interface IServices {

    @GET("/products")
    ArrayList<Product> getProductList();

    @POST("/products")
    Product createProduct(@Body Product product);

    @DELETE("/products/{id}")
    DeleteResponse deleteProduct(@Path("id")String id);


    @GET("/user/auth")
    User auth(@Query("email") String email, @Query("password")String password);

    @GET("/user")
    User autoAuth(@Header("Authorization")String token);

    @POST("/customers")
    Customer createCustomer(@Body Customer customer);

    @GET("/customers")
    ArrayList<Customer> getCustomers();
}
