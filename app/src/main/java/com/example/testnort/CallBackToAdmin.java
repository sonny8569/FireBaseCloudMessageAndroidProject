package com.example.testnort;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.messaging.FirebaseMessaging;

public class CallBackToAdmin extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.callbacktoadmin);
        getSupportActionBar().hide();
    }

    public void GotoMAIN(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
