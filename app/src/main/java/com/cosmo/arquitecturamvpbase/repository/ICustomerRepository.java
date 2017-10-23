package com.cosmo.arquitecturamvpbase.repository;

import com.cosmo.arquitecturamvpbase.model.Customer;

import java.util.ArrayList;

/**
 * Created by user on 03/10/2017.
 */

public interface ICustomerRepository {

    ArrayList<Customer> getCustomers();

    Customer createCustomer(Customer customer);
}
