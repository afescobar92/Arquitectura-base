package com.cosmo.arquitecturamvpbase.repository;

import com.cosmo.arquitecturamvpbase.helper.ServicesFactory;
import com.cosmo.arquitecturamvpbase.model.DeleteResponse;
import com.cosmo.arquitecturamvpbase.model.Product;
import com.cosmo.arquitecturamvpbase.services.IServices;

import java.util.ArrayList;

import retrofit.RetrofitError;

/**
 * Created by leidyzulu on 16/09/17.
 */

public class ProductRepository implements IProductRepository {

    private IServices services;


    public ProductRepository() {
        ServicesFactory servicesFactory = new ServicesFactory();
        services = (IServices) servicesFactory.getInstance(IServices.class);
    }
    @Override
    public ArrayList<Product> getProductList() throws RetrofitError{
        ArrayList<Product>  products = services.getProductList();
        return products;
    }
    @Override
    public Product createProduct(Product product) throws RetrofitError{
        Product productCreated = services.createProduct(product);
        return productCreated;
    }

    @Override
    public DeleteResponse deleteProduct(String id)  throws RepositoryError {
        try {

           return services.deleteProduct(id);

        }catch (RetrofitError retrofitError){

            throw  MapperError.convertRetrofitErrorToRepositoryError(retrofitError);
        }

    }
}
