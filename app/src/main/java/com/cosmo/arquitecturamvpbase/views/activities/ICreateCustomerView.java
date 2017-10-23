package com.cosmo.arquitecturamvpbase.views.activities;

import com.cosmo.arquitecturamvpbase.views.IBaseView;

/**
 * Created by user on 03/10/2017.
 */

public interface ICreateCustomerView extends IBaseView {

    void showAlertDialogInternet(int title, String message);

    void showAlertError(int title, String message);

    void endProcess(boolean create);
}
