package com.cosmo.arquitecturamvpbase.presenter;

import com.cosmo.arquitecturamvpbase.R;
import com.cosmo.arquitecturamvpbase.model.Customer;
import com.cosmo.arquitecturamvpbase.repository.ICustomerRepository;
import com.cosmo.arquitecturamvpbase.repository.MapperError;
import com.cosmo.arquitecturamvpbase.repository.RepositoryError;
import com.cosmo.arquitecturamvpbase.views.activities.ICreateCustomerView;

import retrofit.RetrofitError;

/**
 * Created by user on 03/10/2017.
 */

public class CreateCustomerPresenter extends BasePresenter<ICreateCustomerView> {

    private ICustomerRepository customerRepository;

    public CreateCustomerPresenter(ICustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void setCustomers(Customer sendObject){
        if (getValidateInternet().isConnected()) {
            createThreadSetCustomers(sendObject);
        }else{
            getView().showAlertError(R.string.error, "Error de conectividad");
        }

    }

    public void createThreadSetCustomers(final Customer sendObject){

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                setCustomersRepository(sendObject);
            }
        });
        thread.start();
    }

    public void setCustomersRepository(Customer sendObject){
        try {
            this.customerRepository.createCustomer(sendObject);
            getView().endProcess(true);
        } catch (RetrofitError retrofitError) {

            RepositoryError repositoryError = MapperError.convertRetrofitErrorToRepositoryError(retrofitError);
            getView().showAlertError(R.string.error, repositoryError.getMessage());

        }
    }

}
