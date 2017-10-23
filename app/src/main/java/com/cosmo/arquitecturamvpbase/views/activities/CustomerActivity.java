package com.cosmo.arquitecturamvpbase.views.activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.View;
import android.widget.ListView;
import android.widget.AdapterView;

import com.cosmo.arquitecturamvpbase.R;
import com.cosmo.arquitecturamvpbase.helper.Constants;
import com.cosmo.arquitecturamvpbase.model.Customer;
import com.cosmo.arquitecturamvpbase.presenter.DetailCustomerPresenter;
import com.cosmo.arquitecturamvpbase.repository.CustomerRepository;
import com.cosmo.arquitecturamvpbase.views.BaseActivity;
import com.cosmo.arquitecturamvpbase.views.adapter.CustomerAdapter;

import java.util.ArrayList;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

/**
 * Created by user on 03/10/2017.
 */

public class CustomerActivity  extends BaseActivity<DetailCustomerPresenter> implements IDetailCustomerView {

    private ListView customerList;
    private CustomerAdapter customerAdapter;
    private ContentLoadingProgressBar progress;
    private FloatingActionButton buttonLaunchCreate;

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        setPresenter(new DetailCustomerPresenter(new CustomerRepository()));
        getPresenter().inject(this,getValidateInternet());
        customerList = (ListView) findViewById(R.id.customer_listView);
        progress = (ContentLoadingProgressBar) findViewById(R.id.progress);
        progress.show();
        getPresenter().getCustomers();
        initEvents();
        obtainPermission();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void obtainPermission(){
        if (checkSelfPermission(ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {

                Snackbar.make(customerList, R.string.permission_location, Snackbar.LENGTH_INDEFINITE)
                        .setAction(android.R.string.ok, new View.OnClickListener() {
                            @Override
                            @TargetApi(Build.VERSION_CODES.M)
                            public void onClick(View v) {
                                requestPermissions(new String[]{ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                            }

                        });
            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }

        }
    }

    private void initEvents() {
        buttonLaunchCreate = (FloatingActionButton) findViewById(R.id.fab_launch_createcustomer);
        buttonLaunchCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerActivity.this, CreateCustomerActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getPresenter().getCustomers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        progress.show();
        getPresenter().getCustomers();
    }

    @Override
    public void showCustomersList(final ArrayList<Customer> customerArrayList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progress.hide();
                createAdapter(customerArrayList);
            }
        });
    }

    private void createAdapter(final ArrayList<Customer> customerArrayList){
        customerAdapter =  new CustomerAdapter(this, R.id.customer_listView, customerArrayList);
        customerList.setAdapter(customerAdapter);
        customerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CustomerActivity.this, MapsActivity.class);
                intent.putExtra(Constants.ITEM_CUSTOMER,customerArrayList.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public void showAlertDialogInternet(int title, int message) {
        showAlertDialog(title, getResources().getString(message));
    }

    private void showAlertDialog(final int title, final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getShowAlertDialog().showAlertDialog(title, message, false, R.string.accept, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getPresenter().getCustomers();
                    }
                }, R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
            }
        });
    }

    @Override
    public void showAlertError(int title, String message) {
        showAlertDialog(title, message);
    }



}
