package com.http.lei.sockettool.socket;

import com.http.lei.sockettool.util.LogTool;
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
     * @param host
     * @param port
     * @param data
     * @param callBack
     */
    public static void getUdpConnect(String host,int port,String data,UdpCallBack callBack){
        getInstance()._getUdpConnect(host, port, data, callBack);
    }

    /**
     * @param host
     * @param port
     * @param data
     * @param callBack
     */
    public static void getTcpConnect(String host,int port,String data,UdpCallBack callBack){
        getInstance()._getTcpConnect(host, port, data, callBack);
    }

    private void _getTcpConnect(String host, int port, String data, UdpCallBack callBack) {
        WorkStation.add(new TcpRunnable(host, port, data, callBack));
    }

    public static void close(){
        getInstance()._close();
    }

    private void _close() {
        LogTool.d("close socket");
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

    /*private void _getTcpConnect(String host,int port,String data,UdpCallBack callBack) {
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

    private void _getUdpConnect(String host,int port,String data,UdpCallBack callBack){
        WorkStation.add(new UdpRunnable(host, port, data, callBack));
    }

   /* private void _getUdpConnect(String host,int port,String data,UdpCallBack callBack){
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
*/


}
