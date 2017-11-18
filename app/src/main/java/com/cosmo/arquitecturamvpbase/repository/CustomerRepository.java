package com.cosmo.arquitecturamvpbase.repository;

import com.cosmo.arquitecturamvpbase.helper.ServicesFactory;
import com.cosmo.arquitecturamvpbase.model.Customer;
import com.cosmo.arquitecturamvpbase.services.IServices;

import java.util.ArrayList;

/**
 * Created by user on 03/10/2017.
 */

public class CustomerRepository implements ICustomerRepository {

    private IServices services;

    public CustomerRepository() {
        ServicesFactory servicesFactory = new ServicesFactory(ServicesFactory.TypeConverter.JSON);
        services = (IServices) servicesFactory.getInstance(IServices.class);
    }

    @Override
    public ArrayList<Customer> getCustomers() {
        return services.getCustomers();
    }

    @Override
    public Customer createCustomer(Customer customer) {
        return services.createCustomer(customer);
    }

}
