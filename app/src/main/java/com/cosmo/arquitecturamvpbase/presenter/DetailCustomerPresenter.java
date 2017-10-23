package com.cosmo.arquitecturamvpbase.presenter;

import com.cosmo.arquitecturamvpbase.R;
import com.cosmo.arquitecturamvpbase.model.Customer;
import com.cosmo.arquitecturamvpbase.repository.ICustomerRepository;
import com.cosmo.arquitecturamvpbase.repository.MapperError;
import com.cosmo.arquitecturamvpbase.repository.RepositoryError;
import com.cosmo.arquitecturamvpbase.views.activities.IDetailCustomerView;

import java.util.ArrayList;

import retrofit.RetrofitError;

/**
 * Created by user on 03/10/2017.
 */

public class DetailCustomerPresenter extends BasePresenter<IDetailCustomerView> {

    private ICustomerRepository customerRepository;

    public DetailCustomerPresenter(ICustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void getCustomers(){
        if (getValidateInternet().isConnected()) {
            createThreadGetCustomers();
        }else{
            getView().showAlertError(R.string.error, "Error de conectividad");
        }

    }

    public void createThreadGetCustomers(){

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                getCustomersRepository();
            }
        });
        thread.start();
    }

    public void getCustomersRepository(){
        try {
            ArrayList<Customer> customerArrayList = this.customerRepository.getCustomers();
            getView().showCustomersList(customerArrayList);

        } catch (RetrofitError retrofitError) {

            RepositoryError repositoryError = MapperError.convertRetrofitErrorToRepositoryError(retrofitError);
            getView().showAlertError(R.string.error, repositoryError.getMessage());

        }
    }

}
