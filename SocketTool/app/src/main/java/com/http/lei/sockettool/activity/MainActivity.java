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

    TextView tvShow;
    Button btnShow;
    int PORT = 12345;
    String HOST="192.168.31.237";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        tvShow = (TextView) findViewById(R.id.tvShow);
        btnShow = (Button) findViewById(R.id.btnShow);

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("main", " start ");
                mThread.start();
            }
        });
    }

    Thread mThread = new Thread(new Runnable() {
        @Override
        public void run() {
            SocketProvider.getUdpConnect(PORT, "hello", HOST, new UdpCallBack() {
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
}
