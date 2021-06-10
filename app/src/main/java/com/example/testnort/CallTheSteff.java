package com.example.testnort;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.MapFragment;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CallTheSteff extends AppCompatActivity {
    String Urlstr = "https://fcm.googleapis.com/fcm/send";
    RequestQueue mRequestque;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.callthesteff);
        getSupportActionBar().hide();
        mRequestque = Volley.newRequestQueue(this);
        FirebaseMessaging.getInstance().subscribeToTopic("news");
        SendNotification();

    }

    private void SendNotification(){
        JSONObject MainObject = new JSONObject();
        JSONObject data = new JSONObject();
        try {
            MainObject.put("to", "/topics/"+"news");
            JSONObject NotificationObj = new JSONObject();
            NotificationObj.put("title","도와주세요!");
            NotificationObj.put("body","여긴 한남대학교 디자인펙토리 입니다");
            NotificationObj.put("click_action", "SendTheMessage");
            MainObject.put("notification",NotificationObj);
           JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Urlstr,
                    MainObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }
            ){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String , String> header = new HashMap<>();
                    header.put("content-type","application/json");
                    header.put("authorization","key=AAAAcseWWHo:APA91bEhPpvIr8bYKDwmIFzOQSfAUNBF-WdN3LHvnHX5EFEzSYxst3V7KKJGIod9A6RZGTRwwjOe3oPNDBp3OIngJ9EB--vj4xjFxmRb15TPg1bXAaMAkHU1C40l-7RFEYOvysJbV-rY");
                    return header;
                }
            };
            mRequestque.add(request);

        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
