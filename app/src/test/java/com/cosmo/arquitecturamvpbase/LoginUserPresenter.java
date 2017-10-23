package com.cosmo.arquitecturamvpbase;

import com.cosmo.arquitecturamvpbase.helper.IValidateInternet;
import com.cosmo.arquitecturamvpbase.model.User;
import com.cosmo.arquitecturamvpbase.presenter.CreateProductPresenter;
import com.cosmo.arquitecturamvpbase.presenter.LoginPresenter;
import com.cosmo.arquitecturamvpbase.repository.ILoginRepository;
import com.cosmo.arquitecturamvpbase.views.activities.ILoginView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by user on 14/10/2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class LoginUserPresenter {

    @Mock
    IValidateInternet validateInternet;

    @Mock
    ILoginRepository loginRepository;

    @Mock
    ILoginView loginView;

    @InjectMocks
    User userSession;

    LoginPresenter loginPresenter;



    @Before
    public void setUp() throws Exception{
        loginPresenter = Mockito.spy(new LoginPresenter(loginRepository));
        loginPresenter.inject(loginView, validateInternet);
    }

    @Test
    public void authSession(){
        userSession = getUserSession();
        when(validateInternet.isConnected()).thenReturn(true);
        this.loginPresenter.auth(userSession.getEmail(),userSession.getPassword(),false);
        this.loginPresenter.authSessionRepository(userSession);
        verify(loginPresenter).createThreadAuthSession(userSession);
    }



    User getUserSession(){
        User user = new User();
        user.setName("Andres");
        user.setUsername("andres");
        user.setEmail("andres@gmail.com");
        user.setPassword("123456");
        user.setFollowings(1);
        user.setFollowers(10);
        user.setLikes(201);
        user.setPhoto("https://thumb7.shutterstock.com/display_pic_with_logo/3648824/527474167/stock-vector-head-man-smile-avatar-virtual-reality-icon-527474167.jpg");
        user.setToken("d5aV0jzfISjWV3FyzJJN");
        return user;
    }

}
