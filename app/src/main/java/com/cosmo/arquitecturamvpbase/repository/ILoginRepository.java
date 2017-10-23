package com.cosmo.arquitecturamvpbase.repository;

import com.cosmo.arquitecturamvpbase.model.User;

/**
 * Created by user on 14/10/2017.
 */

public interface ILoginRepository {

    User authSession(User user)throws RepositoryError;

    User autoAuthSession(User user)throws RepositoryError;

}
