package com.example.testnort;

import android.Manifest;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CallTheMap extends AppCompatActivity implements OnMapReadyCallback {
    private static final int REQUEST_CODE_PERMISSION = 1000;
    private static final String CHANNEL_ID = "101";
    private FragmentManager frg;
    private MapFragment mfrg;
    private GoogleApiClient mGoogleApiClient;
    private FusedLocationProviderClient mfuse;
    private GoogleMap gmap;
    String Urlstr = "https://fcm.googleapis.com/fcm/send";
    RequestQueue mRequestque;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.googlemap);
        getSupportActionBar().hide();

        frg = getFragmentManager();
        mfrg = (MapFragment) frg.findFragmentById(R.id.googleMap);
        mfrg.getMapAsync(this);
        mfuse =LocationServices.getFusedLocationProviderClient(this);
        FirebaseMessaging.getInstance().subscribeToTopic("news");


    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            googleMap.setMyLocationEnabled(true);
            return;
        }else{
            Toast.makeText(this,"gps을 켜주세요!",Toast.LENGTH_LONG).show();
        }


    }


    public void click(View view) {
      Intent intent = new Intent(this,CallTheSteff.class);
      startActivity(intent);
    }
}
