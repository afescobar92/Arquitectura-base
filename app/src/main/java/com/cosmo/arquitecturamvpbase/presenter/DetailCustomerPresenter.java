package com.cosmo.arquitecturamvpbase.presenter;

import android.util.Log;

import com.cosmo.arquitecturamvpbase.R;
import com.cosmo.arquitecturamvpbase.model.Customer;
import com.cosmo.arquitecturamvpbase.model.Note;
import com.cosmo.arquitecturamvpbase.repository.ICustomerRepository;
import com.cosmo.arquitecturamvpbase.repository.INotesRepository;
import com.cosmo.arquitecturamvpbase.repository.MapperError;
import com.cosmo.arquitecturamvpbase.repository.NotesRepository;
import com.cosmo.arquitecturamvpbase.repository.RepositoryError;
import com.cosmo.arquitecturamvpbase.views.activities.IDetailCustomerView;

import java.util.ArrayList;

import retrofit.RetrofitError;

/**
 * Created by user on 03/10/2017.
 */

public class DetailCustomerPresenter extends BasePresenter<IDetailCustomerView> {

    private ICustomerRepository customerRepository;
    private INotesRepository notesRepository;

    public DetailCustomerPresenter(ICustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        this.notesRepository = new NotesRepository();
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
            Note note = this.notesRepository.getNote();

            Log.i("NOTE To ",note.getTo());
            Log.i("NOTE From ",note.getFrom());
            Log.i("NOTE Heading ",note.getHeading());
            Log.i("NOTE Body ",note.getBody());

        } catch (RetrofitError retrofitError) {

            RepositoryError repositoryError = MapperError.convertRetrofitErrorToRepositoryError(retrofitError);
            getView().showAlertError(R.string.error, repositoryError.getMessage());

        }
    }

}
