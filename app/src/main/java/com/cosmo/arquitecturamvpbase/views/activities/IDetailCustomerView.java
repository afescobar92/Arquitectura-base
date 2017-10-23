package com.cosmo.arquitecturamvpbase.views.activities;

import com.cosmo.arquitecturamvpbase.model.Customer;
import com.cosmo.arquitecturamvpbase.views.IBaseView;

import java.util.ArrayList;

/**
 * Created by user on 03/10/2017.
 */

public interface IDetailCustomerView extends IBaseView {

    void showCustomersList(ArrayList<Customer> customerArrayList);

    void showAlertDialogInternet(int title, int message);

    void showAlertError(int title, String message);
}
