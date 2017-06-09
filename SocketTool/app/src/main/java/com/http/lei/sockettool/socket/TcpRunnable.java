package com.http.lei.sockettool.socket;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by lei on 2017/6/9.
 */
public class TcpRunnable implements Runnable {

    private Socket socketTcp = null;
    String host;
    int port;
    String data;
    UdpCallBack callBack;

    public TcpRunnable(String host,int port,String data,UdpCallBack callBack){
        this.host = host;
        this.port = port;
        this.data = data;
        this.callBack = callBack;
    }

    @Override
    public void run() {
        try {
            socketTcp = new Socket(host, port);
            SocketUtil util = new SocketUtil(socketTcp);
            util.sendData(data);
            boolean receiveState = true;
            while (receiveState){
                String str = util.receiveData();
                callBack.receive(str);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   /* private void _getTcpConnect(String host,int port,String data,UdpCallBack callBack) {
        try {
            socketTcp = new Socket(host, port);
            SocketUtil util = new SocketUtil(socketTcp);
            util.sendData(data);

            boolean receiveState = true;
            while (receiveState){
                String str = util.receiveData();
                callBack.receive(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
