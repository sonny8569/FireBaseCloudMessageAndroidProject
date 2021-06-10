package com.example.testnort;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Handler;

public class MainActivity extends AppCompatActivity  {
    String Urlstr = "https://fcm.googleapis.com/fcm/send";
    RequestQueue mRequestque;
    String Sdevice="HC-06";
    BluetoothDevice device;
    ArrayAdapter arrayAdapter ;
    private static String address = "00:20:10:08:D4:53";
    private IntentIntegrator qrScan;
    private static final int REQUEST_ENABLE_BT=10;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothSocket bluetoothSocket = null;
    private OutputStream outputStream = null;
    private InputStream inputStream = null;
    private Thread workerThread = null;
    private Handler mHandler;
    String CHANNEL_ID = "101";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent Startintro = new Intent(getApplicationContext(),Intro.class);
        startActivity(Startintro);
        mRequestque = Volley.newRequestQueue(this);
        FirebaseMessaging.getInstance().subscribeToTopic("news");
       bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();//블루트스할 변수 선언
        try {
            openBT();//소켓열준비
        } catch (IOException e) {
            e.printStackTrace();//연결을 못했을시 그냥 pass
        }
        createNotificationChannel();//백엔드알림

    }

    public void Monclick(View view) {
        //qr코드 스캔부분
        qrScan = new IntentIntegrator(this);
        qrScan.setOrientationLocked(true);
        qrScan.initiateScan();

    }

    public void CallTheStaff(View view) {
        Intent CallTheGoogleMap = new Intent(getApplicationContext(),CallTheMap.class);
        startActivity(CallTheGoogleMap) ;
    }
    void SendData(String result){
        try {
            outputStream.write(result.getBytes());//결과값을 바이트로 쪼게서 블루투스로 쓸준비
        }catch (Exception e){
            Toast.makeText(this,"문자열전송중 문제 발생",Toast.LENGTH_LONG).show();
        }
    }
    void openBT() throws IOException {
        if(!bluetoothAdapter.isEnabled()){
            // Toast.makeText(this, "블루투스안켜져있음",Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(this, "블루투스켜져있음",Toast.LENGTH_LONG).show();
        }
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if(pairedDevices.size() > 0) {
            for(BluetoothDevice device1 : pairedDevices) {
                if(device1.getName().equals("HC-06")) {
                    device = device1;
                    break;
                }
            }
        }
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //내 hc-06 mac주소
        bluetoothSocket = device.createRfcommSocketToServiceRecord(uuid);//소켓 오픈
        bluetoothSocket.connect();//안드로이드 hc-06연결
        outputStream = bluetoothSocket.getOutputStream();//qr값 전송
        inputStream =bluetoothSocket.getInputStream();//이거또한 혹시 qt값 읽을수 있으니 선언만했음

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();//qr 코드 읽고 있을때 뒤로가기 눌렀을시
            } else {
                Toast.makeText(this, "Scanned" + result.getContents(), Toast.LENGTH_LONG).show();
                String send = result.getContents();//qr값을 읽었을시
                SendData(send);//이 함수로 보냄
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }


    }
    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name ="FireBaseNoticeChannel";
            String description = "This is the channel to recive firbase notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    public void ClickHowToUse(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("사용법")
                .setMessage("1.무빙워크 앞 QR코드를 스캔하세요\n" +
                        "2.휠채어 앞바퀴를 무빙워크 레일에 올리고 대기해 주세요\n"
                +"3.탑승시 휠체어 브레이크를 사용해주세요\n"
                +"4.스캔 부분이 최상층에 올라왔을때 브레이크를 해제해 주세요.\n"
                +"5.안전하게 내려주세요");
        builder.setPositiveButton("DONE",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();



    }
}