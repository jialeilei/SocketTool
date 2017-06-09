package com.http.lei.sockettool.socket;

import com.http.lei.sockettool.util.LogTool;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by lei on 2017/6/9.
 */
public class SocketUtil {
    Socket socket = null;

    public SocketUtil(Socket socket){
        this.socket = socket;
    }

    public boolean isConnect(){
        if (socket.isClosed()){
            // TODO: 2017/6/9
            return false;
        }
        return true;
    }

    public void sendData(String data) throws IOException {
        OutputStream stream = socket.getOutputStream();
        stream.write(data.getBytes());
    }

    public String receiveData(){
        InputStream stream = null;
        String data ="";
        try {
            stream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int len = stream.read(bytes);
            data = new String(bytes,0,len);
            LogTool.d("len: "+len+" data.length: "+data.length());

        } catch (IOException e) {
            e.printStackTrace();
            return data;
        }

        return data;
    }


}
