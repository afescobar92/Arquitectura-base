package com.cosmo.arquitecturamvpbase.presenter;
import com.cosmo.arquitecturamvpbase.R;
import com.cosmo.arquitecturamvpbase.model.User;
import com.cosmo.arquitecturamvpbase.repository.ILoginRepository;
import com.cosmo.arquitecturamvpbase.repository.LoginRepository;
import com.cosmo.arquitecturamvpbase.repository.MapperError;
import com.cosmo.arquitecturamvpbase.repository.RepositoryError;
import com.cosmo.arquitecturamvpbase.views.activities.ILoginView;

import retrofit.RetrofitError;

/**
 * Created by user on 14/10/2017.
 */

public class LoginPresenter extends BasePresenter<ILoginView> {

    private ILoginRepository loginRepository;

    public LoginPresenter(ILoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public LoginPresenter() {
        this.loginRepository = new LoginRepository();
    }

    public void auth(String email, String pass, boolean login){
        if(getValidateInternet().isConnected()){
            User user = new User();
            if(login) {
                user.setEmail(email);
                user.setPassword(pass);
                createThreadAuthSession(user);
            }else{
                user = getView().getUserSession();
                createThreadAutoAuthSession(user);
            }
        }else{
            getView().showAlertInternet(R.string.error, R.string.validate_internet);
        }
    }



    public void createThreadAuthSession(final User user){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                authSessionRepository(user);
            }
        });
        thread.start();
    }

    public void authSessionRepository(User user){
        try{
            User response = this.loginRepository.authSession(user);
            getView().setUserSession(response);
        }catch (RepositoryError repositoryError){
            getView().showToast(repositoryError.getMessage());
        }finally {
            getView().hideProgress();
        }

    }

    public void createThreadAutoAuthSession(final User user){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                autoAuthSessionRepository(user);
            }
        });
        thread.start();
    }

    public void autoAuthSessionRepository(User user){
        try{
            User response = this.loginRepository.autoAuthSession(user);
            getView().setUserSession(response);
        }catch (RepositoryError repositoryError){
            getView().showToast(repositoryError.getMessage());
        }finally {
            getView().hideProgress();
        }

    }



}
