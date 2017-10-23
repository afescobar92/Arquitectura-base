package com.cosmo.arquitecturamvpbase.views.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cosmo.arquitecturamvpbase.R;
import com.cosmo.arquitecturamvpbase.helper.Constants;
import com.cosmo.arquitecturamvpbase.helper.CustomSharePreference;
import com.cosmo.arquitecturamvpbase.model.User;
import com.squareup.picasso.Picasso;

/**
 * Created by user on 17/10/2017.
 */

public class ProfileFragment extends BaseFragment  {

    TextView username,followings,followers,likes;
    ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile,container,false);
        loadView(view);
        loadContent();
        return view;
    }

    private void loadView(View view) {
        username = (TextView) view.findViewById(R.id.username);
        followings = (TextView) view.findViewById(R.id.followings);
        followers = (TextView) view.findViewById(R.id.followers);
        likes = (TextView) view.findViewById(R.id.likes);
        imageView = (ImageView) view.findViewById(R.id.ide_img);
    }

    private void loadContent() {
        CustomSharePreference customSharePreference = new CustomSharePreference(getActivity());
        User userSession = (User) customSharePreference.getObject(Constants.USER_OBJECT,User.class);
        username.setText("@"+userSession.getUsername());
        followings.setText(String.valueOf(userSession.getFollowings()));
        followers.setText(String.valueOf(userSession.getFollowers()));
        likes.setText(String.valueOf(userSession.getLikes()));
        Picasso.with(getActivity()).load(userSession.getPhoto()).into(imageView);
    }

}
