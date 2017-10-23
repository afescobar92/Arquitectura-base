package com.cosmo.arquitecturamvpbase.views.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cosmo.arquitecturamvpbase.R;
import com.cosmo.arquitecturamvpbase.model.Customer;
import com.cosmo.arquitecturamvpbase.model.Location;
import com.cosmo.arquitecturamvpbase.model.Phone;
import com.cosmo.arquitecturamvpbase.presenter.CreateCustomerPresenter;
import com.cosmo.arquitecturamvpbase.repository.CustomerRepository;
import com.cosmo.arquitecturamvpbase.views.BaseActivity;

import java.util.ArrayList;

/**
 * Created by user on 03/10/2017.
 */

public class CreateCustomerActivity extends BaseActivity<CreateCustomerPresenter> implements ICreateCustomerView,TextWatcher {

    private EditText customer_name, customer_surname, customer_number_phone, customer_descripcion_phone,
            customer_location_type_phone,customer_location_coordinates_phone;
    private Button customer_btnCreate;
    private ContentLoadingProgressBar progress;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_customer);
        setPresenter(new CreateCustomerPresenter(new CustomerRepository()));
        getPresenter().inject(this,getValidateInternet());
        initComponents();
        initEvents();
    }

    private void initComponents(){
        progress = (ContentLoadingProgressBar) findViewById(R.id.progress);
        progress.hide();
        customer_name = (EditText) findViewById(R.id.customer_name);
        customer_name.addTextChangedListener(this);
        customer_surname = (EditText) findViewById(R.id.customer_surname);
        customer_surname.addTextChangedListener(this);
        customer_number_phone = (EditText) findViewById(R.id.customer_number_phone);
        customer_number_phone.addTextChangedListener(this);
        customer_descripcion_phone = (EditText) findViewById(R.id.customer_descripcion_phone);
        customer_descripcion_phone.addTextChangedListener(this);
        customer_location_type_phone = (EditText) findViewById(R.id.customer_location_type_phone);
        customer_location_type_phone.addTextChangedListener(this);
        customer_location_coordinates_phone = (EditText) findViewById(R.id.customer_location_coordinates_phone);
        customer_location_coordinates_phone.addTextChangedListener(this);
        customer_btnCreate = (Button) findViewById(R.id.customer_btnCreate);
    }

    private void initEvents(){

        progress.show();
        customer_btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().setCustomers(getCustomer());
            }
        });
    }


    public Customer getCustomer(){

        Customer returnObject = new Customer();
        Phone phone = new Phone();
        Location location = new Location();
        ArrayList<Phone> arrayListPhone = new ArrayList<>();

        phone.setNumber(customer_number_phone.getText().toString());

        location.setCoordinates(new double[]{Double.parseDouble(customer_location_coordinates_phone.getText().toString())});

        location.setType(customer_location_type_phone.getText().toString());
        phone.setLocations(location);

        arrayListPhone.add(phone);

        returnObject.setName(customer_name.getText().toString());
        returnObject.setSurname(customer_surname.getText().toString());
        returnObject.setPhoneList(arrayListPhone);

        return returnObject;
    }

    @Override
    public void endProcess(final boolean create) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progress.hide();
                if(create){
                    Toast.makeText(CreateCustomerActivity.this, getResources().getString(R.string.okResponseCreateCustomer), Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    Toast.makeText(CreateCustomerActivity.this, getResources().getString(R.string.errResponseCreateCustomer), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void showAlertDialogInternet(int title, String message) {

    }

    @Override
    public void showAlertError(int title, String message) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        this.customer_btnCreate.setEnabled(validateForm());
    }

    private boolean validateForm(){
        return !customer_name.getText().toString().isEmpty()
                && !customer_surname.getText().toString().isEmpty()
                && !customer_number_phone.getText().toString().isEmpty()
                && !customer_descripcion_phone.getText().toString().isEmpty()
                && !customer_descripcion_phone.getText().toString().isEmpty()
                && !customer_location_type_phone.getText().toString().isEmpty()
                && !customer_location_coordinates_phone.getText().toString().isEmpty();

    }

}
