package com.cosmo.arquitecturamvpbase.repository;

import com.cosmo.arquitecturamvpbase.helper.ServicesFactory;
import com.cosmo.arquitecturamvpbase.model.User;
import com.cosmo.arquitecturamvpbase.services.IServices;

import retrofit.RetrofitError;

/**
 * Created by user on 14/10/2017.
 */

public class LoginRepository implements ILoginRepository {

    private IServices services;

    public LoginRepository() {
        ServicesFactory servicesFactory = new ServicesFactory();
        this.services = (IServices) servicesFactory.getInstance(IServices.class);
    }

    @Override
    public User authSession(User user)throws RepositoryError {
        try {
            return services.auth(user.getEmail(),user.getPassword());
        }catch (RetrofitError retrofitError){
            throw  MapperError.convertRetrofitErrorToRepositoryError(retrofitError);
        }
    }

    @Override
    public User autoAuthSession(User user) throws RepositoryError {
        try {
            return services.autoAuth("Bearer: "+user.getToken());
        }catch (RetrofitError retrofitError){
            throw  MapperError.convertRetrofitErrorToRepositoryError(retrofitError);
        }
    }
}
