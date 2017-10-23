package com.cosmo.arquitecturamvpbase.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

/**
 * Created by user on 17/10/2017.
 */

public class CustomSharePreference {

    private SharedPreferences sharedPreferences;
    private Class aClass;

    public CustomSharePreference(Context  context) {
        this.sharedPreferences = context.getSharedPreferences(Constants.SHARE_PREFERENCE,Context.MODE_PRIVATE);
    }

    public void saveObject(String key,Object object){
        Gson gson = new Gson();
        String json = gson.toJson(object);
        addValue(key,json);
    }

    private void addValue(String key,String json){
       this.sharedPreferences.edit().putString(key,json).commit();
    }

    public Object getObject(String key,Class aClass){
        Gson gson = new Gson();
        return gson.fromJson(this.sharedPreferences.getString(key,""), aClass);
    }

    public void removeObject(String key){
        this.sharedPreferences.edit().remove(key).commit();
    }


}
