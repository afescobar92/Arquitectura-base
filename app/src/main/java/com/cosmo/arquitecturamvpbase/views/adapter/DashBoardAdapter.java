package com.cosmo.arquitecturamvpbase.views.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cosmo.arquitecturamvpbase.helper.Constants;
import com.cosmo.arquitecturamvpbase.views.fragments.CustomerFragment;
import com.cosmo.arquitecturamvpbase.views.fragments.ProductFragment;
import com.cosmo.arquitecturamvpbase.views.fragments.ProfileFragment;

/**
 * Created by user on 14/10/2017.
 */

public class DashBoardAdapter extends FragmentStatePagerAdapter {

    public DashBoardAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new ProductFragment();
            case 1:
                return new CustomerFragment();
            case 2:
                return new ProfileFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return Constants.TITLE_PRODUCTO;
            case 1:
                return Constants.TITLE_CONTACTO;
            case 2:
                return Constants.TITLE_PROFILE;
            default:
                return Constants.TITLE_EMPTY;
        }
    }
}
