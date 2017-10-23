package com.cosmo.arquitecturamvpbase.views.activities;

import com.cosmo.arquitecturamvpbase.model.User;
import com.cosmo.arquitecturamvpbase.views.IBaseView;

/**
 * Created by user on 14/10/2017.
 */

public interface ILoginView extends IBaseView {

    void setUserSession(User userSession);

    void showAlertInternet(int title, int message);

    void showAlertError(int title, String message);

    void showToast(int message);

    void showToast(String message);

    User getUserSession();

}
