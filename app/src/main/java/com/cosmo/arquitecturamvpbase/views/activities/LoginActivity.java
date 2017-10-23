package com.cosmo.arquitecturamvpbase.views.activities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cosmo.arquitecturamvpbase.R;
import com.cosmo.arquitecturamvpbase.helper.Constants;
import com.cosmo.arquitecturamvpbase.helper.CustomSharePreference;
import com.cosmo.arquitecturamvpbase.model.User;
import com.cosmo.arquitecturamvpbase.presenter.LoginPresenter;
import com.cosmo.arquitecturamvpbase.repository.LoginRepository;
import com.cosmo.arquitecturamvpbase.views.BaseActivity;

public class LoginActivity extends BaseActivity<LoginPresenter> implements ILoginView, TextWatcher {

    private EditText login_etEmail, login_etPass;
    private Button login_btnLogin;
    private boolean login = Boolean.TRUE;
    User userSession;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setPresenter(new LoginPresenter());
        getPresenter().inject(this, getValidateInternet());
        createProgressDialog();
        loadViews();
        loadEvents();
        validateLogin();
    }

    private void validateLogin() {
        CustomSharePreference customSharePreference = new CustomSharePreference(this);
        userSession = (User) customSharePreference.getObject(Constants.USER_OBJECT,User.class);
        if(userSession != null){
            if(userSession.getToken()!= null && !userSession.getToken().isEmpty()){
                this.login = Boolean.FALSE;
                getPresenter().auth(login_etEmail.getText().toString(), login_etPass.getText().toString(),login);
            }
        }
    }


    private void loadViews() {
        login_etEmail = (EditText) findViewById(R.id.login_etEmail);
        login_etEmail.addTextChangedListener(this);
        login_etPass = (EditText) findViewById(R.id.login_etPass);
        login_etPass.addTextChangedListener(this);
        login_btnLogin = (Button) findViewById(R.id.login_btnLogin);
    }

    private void loadEvents(){

        login_btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().auth(login_etEmail.getText().toString(), login_etPass.getText().toString(),login);
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!login_etEmail.getText().toString().trim().isEmpty() && !login_etEmail.getText().toString().trim().isEmpty()) {
            login_btnLogin.setBackgroundResource(R.color.colorPrimary);
            login_btnLogin.setEnabled(true);
        }else{
            login_btnLogin.setBackgroundResource(R.color.colorGray);
            login_btnLogin.setEnabled(false);
        }
    }

    @Override
    public void setUserSession(User userSession) {
        CustomSharePreference customSharePreference = new CustomSharePreference(LoginActivity.this);
        customSharePreference.saveObject(Constants.USER_OBJECT,userSession);
        Intent intent = new Intent(LoginActivity.this, DashBoardActivity.class);
        startActivity(intent);
    }

    @Override
    public void showAlertInternet(int title, int message) {

    }

    @Override
    public void showAlertError(int title, String message) {

    }

    @Override
    public void showToast(int message) {

    }

    @Override
    public void showToast(String message) {

    }

    @Override
    public User getUserSession() {
        return this.userSession;
    }
}
