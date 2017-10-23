package com.cosmo.arquitecturamvpbase.views.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cosmo.arquitecturamvpbase.R;
import com.cosmo.arquitecturamvpbase.model.Customer;

import java.util.ArrayList;

/**
 * Created by user on 05/10/2017.
 */

public class CustomerAdapter extends ArrayAdapter<Customer> {

    private ArrayList<Customer> customerArrayList;
    private Activity context;
    private Customer customer;
    private TextView name;


    public CustomerAdapter(Activity context, int resource, ArrayList<Customer> customerArrayList) {
        super(context, resource, customerArrayList);
        this.context = context;
        this.customerArrayList = customerArrayList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_item, parent, false);
        this.customer = this.customerArrayList.get(position);
        loadView(convertView);
        name.setText(customer.getName());
        return convertView;
    }

    private void loadView(View convertView){
        name = (TextView) convertView.findViewById(R.id.item_name_customer);
    }

}
