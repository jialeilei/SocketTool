package com.http.lei.sockettool.socket;

import com.http.lei.sockettool.util.LogTool;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by lei on 2017/6/9.
 */
public class UdpRunnable implements Runnable{

    private DatagramSocket socketUdp = null;
    private String host;
    private int port;
    private String data;
    private UdpCallBack callBack;

    public UdpRunnable(String host,int port,String data,UdpCallBack callBack){
        this.host = host;
        this.port = port;
        this.data = data;
        this.callBack = callBack;
    }

    @Override
    public void run() {

        try {
            InetAddress address = InetAddress.getByName(host);
            byte[] bytes = data.getBytes();
            DatagramPacket packet = new DatagramPacket(bytes,bytes.length,address,port);
            socketUdp = new DatagramSocket();
            //send data
            socketUdp.send(packet);
            System.out.println("已经发送完成");
            //receive data
            boolean send = true;
            while (send){
                byte[] receiveBytes = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveBytes,receiveBytes.length);
                System.out.println("等待服务器数据：");
                socketUdp.receive(receivePacket);
                String responseStr = new String(receiveBytes,0,receiveBytes.length);
                callBack.receive(responseStr);
                System.out.println("服务器返回数据：" + responseStr);
                send = false;
            }
            _close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void _close() {
        LogTool.d("close socket");
        if (socketUdp != null){
            socketUdp.close();
        }
    }

}
