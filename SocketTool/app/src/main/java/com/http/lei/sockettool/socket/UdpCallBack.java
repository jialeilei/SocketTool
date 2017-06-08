package com.http.lei.sockettool.socket;

/**
 * Created by lei on 2017/6/8.
 */
public interface UdpCallBack {

    void receive(String str);

    void isConnect(boolean state);

}
