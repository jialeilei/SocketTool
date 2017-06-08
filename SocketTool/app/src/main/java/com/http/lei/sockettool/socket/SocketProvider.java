package com.http.lei.sockettool.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lei on 2017/6/8.
 */
public class SocketProvider {

    private static Socket mSocket = null;
    private static DataInputStream mInputStream = null;
    private static DataOutputStream mOutputStream = null;
    private Timer heartBeatTimer;
    private TimerTask heartBeatTask;
    private DatagramSocket socketUdp = null;
    private Socket socketTcp = null;
    private static SocketProvider mSocketProvider;

    private SocketProvider(){}

    private static SocketProvider getInstance(){
        if (mSocketProvider == null){
            synchronized (SocketProvider.class){
                if (mSocketProvider == null){
                    mSocketProvider = new SocketProvider();
                }
            }
        }
        return mSocketProvider;
    }


    /**
     * @param port
     * @param data
     * @param host
     * @param callBack
     */
    public static void getUdpConnect(int port,String data,String host,UdpCallBack callBack){
        getInstance()._getUdpConnect(port, data, host, callBack);
    }

    public static void close(){
        getInstance()._close();
    }

    private void _close() {
        if (socketUdp != null){
            socketUdp.close();
        }else if (socketTcp != null){
            try {
                socketTcp.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void _getUdpConnect(int port,String data,String host,UdpCallBack callBack){

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
                socketUdp.receive(receivePacket);
                String responseStr = new String(receiveBytes,0,receiveBytes.length);
                callBack.receive(responseStr);
                System.out.println("我是客户端，服务器说：" + responseStr);
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
