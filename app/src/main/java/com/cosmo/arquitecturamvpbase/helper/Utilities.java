package com.cosmo.arquitecturamvpbase.helper;

import java.util.Calendar;

public class Utilities {


    public static boolean isNight(){
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 0 && timeOfDay < 18){
            return false;
        }else if(timeOfDay >= 18 && timeOfDay < 24){
            return true;
        }
        return false;
    }

}
