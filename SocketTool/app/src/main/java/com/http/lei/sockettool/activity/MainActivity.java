package com.http.lei.sockettool.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.http.lei.sockettool.R;
import com.http.lei.sockettool.socket.SocketProvider;
import com.http.lei.sockettool.socket.UdpCallBack;

public class MainActivity extends AppCompatActivity {

    private TextView tvShow;
    private Button btnUdp, btnTcp;
    private int PORT = 12345;
    private String HOST="10.0.0.14";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        tvShow = (TextView) findViewById(R.id.tvShow);
        btnUdp = (Button) findViewById(R.id.btnShow);
        btnTcp = (Button) findViewById(R.id.btnTcp);

        btnUdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("main", " start ");
                //mThread.start();
                SocketProvider.getUdpConnect(HOST, PORT, "hello", new UdpCallBack() {
                    @Override
                    public void receive(String str) {
                        Log.i("main", " " + str);
                        //tvShow.setText(str+"");
                    }

                    @Override
                    public void isConnect(boolean state) {
                        Log.i("main", " " + state);
                        //tvShow.setText(" "+ state);
                    }
                });
            }
        });
        
        btnTcp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SocketProvider.getTcpConnect(HOST, PORT, "hello,tcp", new UdpCallBack() {
                    @Override
                    public void receive(String str) {
                        Log.i("main", " tcp: " + str);
                    }

                    @Override
                    public void isConnect(boolean state) {
                        Log.i("main", " " + state);
                    }
                });
            }
        });
    }

}
