package com.cosmo.arquitecturamvpbase;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.cosmo.arquitecturamvpbase.model.Product;
import com.cosmo.arquitecturamvpbase.views.activities.DashBoardActivity;
import com.onesignal.OSNotification;
import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONObject;

/**
 * Created by user on 07/11/2017.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        settingOneSignal();
    }

    private void settingOneSignal(){
        OneSignal.startInit(this)
        .autoPromptLocation(false)
        .setNotificationReceivedHandler(new CustomNotificationRecivedHandler())
        .setNotificationOpenedHandler(new CustomNotificationOpenedHandler())
        .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
        .unsubscribeWhenNotificationsAreDisabled(true)
        .init();

    }

    private class CustomNotificationRecivedHandler implements OneSignal.NotificationReceivedHandler{

        @Override
        public void notificationReceived(OSNotification notification) {
            JSONObject data = notification.payload.additionalData;
            String notificationID = notification.payload.notificationID;
            String title = notification.payload.title,body = notification.payload.body;
            Log.i("ONESIGNAL","NOTIFICACION "+notificationID);
            if(data != null){
                String customKey = data.optString("customkey",null);
                if(customKey != null){
                    Log.i("ONESIGNALE","Token customer "+customKey);
                }
            }

        }
    }


    private class CustomNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler{

        @Override
        public void notificationOpened(OSNotificationOpenResult result) {

            OSNotificationAction.ActionType actionType = result.action.type;
            if(actionType != OSNotificationAction.ActionType.ActionTaken){
                Intent intent = new Intent(getApplicationContext(), Product.class);
                startActivity(intent);
            }

        }
    }

}
