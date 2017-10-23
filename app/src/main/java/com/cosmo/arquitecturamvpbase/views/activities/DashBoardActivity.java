package com.cosmo.arquitecturamvpbase.views.activities;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cosmo.arquitecturamvpbase.R;
import com.cosmo.arquitecturamvpbase.views.BaseActivity;
import com.cosmo.arquitecturamvpbase.views.adapter.DashBoardAdapter;

public class DashBoardActivity extends BaseActivity {

    TabLayout dashTabLayout;
    ViewPager dashViewPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        initComponents();
        initEvents();
        initAdapters();
    }

    private void initComponents(){
        this.dashTabLayout = (TabLayout) findViewById(R.id.dash_tab_layout);
        this.dashViewPager = (ViewPager)findViewById(R.id.dash_view_pager);
    }

    private void initEvents(){
    }

    private void initAdapters(){
        DashBoardAdapter dashBoardAdapter = new DashBoardAdapter(getSupportFragmentManager());
        dashViewPager.setAdapter(dashBoardAdapter);
        dashTabLayout.setupWithViewPager(dashViewPager);
        dashTabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));
        dashTabLayout.setTabTextColors(ColorStateList.valueOf(ContextCompat.getColor(this,R.color.colorPrimaryDark)));
    }

}
