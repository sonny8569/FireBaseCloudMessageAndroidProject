package com.example.testnort;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.telecom.Call;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.RemoteMessage;

public class FirBaseMessgingService extends com.google.firebase.messaging.FirebaseMessagingService{


    private static final String TAG = "FireBaseMsgService";
    private static final String ServerKey = "AAAA4hGV538:APA91bE-FdHUp52QtQqDXTnbN0wcIKhhsefEB9ir8okIAvKbtiUniRbVKe5XjD0JiisbhKq_QgVlovK7lyVdAk9awx9zAnMbopuTjPOG0oUk7uuNaFMoMWF3JyHz7xiLD7qmkTUo7_Lk";
    private static final String SendId = "970957645695";

    private String msg,title,clickAction;
    String channelId = "Chabbel ID";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.e(TAG,"onMessageRecevied");

        title = remoteMessage.getNotification().getTitle();
        msg = remoteMessage.getNotification().getBody();
        clickAction = remoteMessage.getNotification().getClickAction();

        Intent intent = new Intent(getApplicationContext(), CallBackToAdmin.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent com = PendingIntent.getActivity(this,1,intent,0);

        NotificationCompat.Builder mb = new NotificationCompat.Builder(this).setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(msg)
                .setAutoCancel(true)
                .setContentIntent(com);

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(getApplicationContext());

        notificationManager.notify(0 /* ID of notification */,mb.build());




    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        sendRegistrationToServer(s);
    }
    private void sendRegistrationToServer(String token){
        String sendToken = token;

    }





}