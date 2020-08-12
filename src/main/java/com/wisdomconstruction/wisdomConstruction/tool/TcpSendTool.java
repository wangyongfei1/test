package com.wisdomconstruction.wisdomConstruction.tool;

import lombok.extern.slf4j.Slf4j;
import java.io.*;
import java.net.Socket;
import java.util.Arrays;

@Slf4j
public class TcpSendTool {

    public static String sendInstruction(String ip, int port, byte[] b) {
        // 创建输入输出数据流
        Socket s = null;
        DataOutputStream dos = null;
        DataInputStream dis = null;
        try {
            s = new Socket(ip, port);//需要数据传向的地址端口传输是透明传输由路由器解决不用考虑ip包的打包及解包
            dos = new DataOutputStream(s.getOutputStream());
            // 发送数据
            log.info("发送数据");
            String s1 = bytesToString(b, 8);
            dos.write(b);
            log.info(s1);
            dos.flush();
            //s.shutdownOutput();
            //接收数据
            log.info("接收数据");
           /* dis = new DataInputStream(s.getInputStream());
            byte[] data = new byte[30];
            String s2 = dis.readUTF();
            log.info(s2);
            log.info(Arrays.toString(data));*/
            BufferedReader rd = new BufferedReader(new InputStreamReader(s.getInputStream(),
                    "UTF-8"));
            String str;
            while ((str = rd.readLine()) != null) {
                log.info(str);
            }
            return Arrays.toString(b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //4、关闭资源
            try {
                s.close();
                dis.close();
                dos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return "";
    }

    //16进制byte数组转string
    private static String bytesToString(byte[] arg, int length) {
        String result = new String();
        if (arg != null) {
            for (int i = 0; i < length; i++) {
                result = result
                        + (Integer.toHexString(
                        arg[i] < 0 ? arg[i] + 256 : arg[i]).length() == 1 ? "0"
                        + Integer.toHexString(arg[i] < 0 ? arg[i] + 256
                        : arg[i])
                        : Integer.toHexString(arg[i] < 0 ? arg[i] + 256
                        : arg[i])) + " ";
            }
            return result;
        }
        return "";
    }

    private static byte[] readStreamByte(Socket socket) throws Exception {
        int readIndex = 50;
        byte[] data = new byte[readIndex];
        InputStream in;
        try {
            in = socket.getInputStream();
            in.read(data, 0, readIndex);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
