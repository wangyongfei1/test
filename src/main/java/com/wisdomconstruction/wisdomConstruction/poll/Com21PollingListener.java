package com.wisdomconstruction.wisdomConstruction.poll;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.comm.CommPortIdentifier;
import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;

/**
 * Com21PollingListener类使用“轮训”的方法监听串口COM21，
 * 并通过COM21的输入流对象来获取该端口接收到的数据（在本文中数据来自串口COM11）。
 */
public class Com21PollingListener {


    public static void main(String[] args){
       // scanPorts();
        //1.定义变量
        CommPortIdentifier com21 = null;//未打卡的端口
        SerialPort serialCom21 = null;//打开的端口
        InputStream inputStream = null;//端口输入流
        OutputStream outputStream = null;//端口输入流

        try{
            //2.获取并打开串口COM21
            com21 = CommPortIdentifier.getPortIdentifier("COM4");
            serialCom21 = (SerialPort) com21.open("Com21Listener", 1000);
            byte[] b = new byte[8];
            b[0] = (byte) 0x01;
            b[1] = (byte) 0x03;
            b[2] = (byte) 0x00;
            b[3] = (byte) 0x00;
            b[4] = (byte) 0x00;
            b[5] = (byte) 0x02;
            b[6] = (byte) 0xC4;
            b[7] = (byte) 0x0B;
            //3.获取串口的输入流对象
            inputStream = serialCom21.getInputStream();
            outputStream = serialCom21.getOutputStream();
            outputStream.write(b);
            outputStream.flush();
            System.out.println("获取数据....");
            byte[] cache = new byte[1024];
            inputStream.read(cache,0,cache.length);
            System.out.println("响应数据:"+cache);
            /*//4.从串口读入数据
            //定义用于缓存读入数据的数组
            byte[] cache = new byte[1024];
            //记录已经到达串口COM21且未被读取的数据的字节（Byte）数。
            int availableBytes = 0;

            //无限循环，每隔20毫秒对串口COM21进行一次扫描，检查是否有数据到达
            while(true){
                //获取串口COM21收到的可用字节数
                availableBytes = inputStream.available();
                //如果可用字节数大于零则开始循环并获取数据
                while(availableBytes > 0){
                    //从串口的输入流对象中读入数据并将数据存放到缓存数组中
                    inputStream.read(cache);
                    //将获取到的数据进行转码并输出
                    for(int j = 0;j < cache.length && j < availableBytes; j++){
                        //因为COM11口发送的是使用byte数组表示的字符串，
                        //所以在此将接收到的每个字节的数据都强制装换为char对象即可，
                        //这是一个简单的编码转换，读者可以根据需要进行更加复杂的编码转换。
                        System.out.print((char)cache[j]);
                    }
                    System.out.println();
                    //更新循环条件
                    availableBytes = inputStream.available();
                }
                //让线程睡眠20毫秒
                Thread.sleep(20);
            }*/
        } catch (Exception e) {
            //如果获取输出流失败，则抛出该异常
            e.printStackTrace();
        }
    }

    /**
     * 扫描本机的所有COM端口
     * @since 2012-3-23 上午12:02:42
     */
    public static void scanPorts() {
        ArrayList<String> portList = new ArrayList<>();
        Enumeration<?> en = CommPortIdentifier.getPortIdentifiers();
        CommPortIdentifier portId;
        while(en.hasMoreElements()){
            portId = (CommPortIdentifier) en.nextElement();
            if(portId.getPortType() == CommPortIdentifier.PORT_SERIAL){
                String name = portId.getName();
                if(!portList.contains(name)) {
                    portList.add(name);
                }
            }
        }
        if(null == portList
                || portList.isEmpty()) {
            //showErrMesgbox("未找到可用的串行端口号,程序无法启动!");
            System.exit(0);
        }
        System.out.println("串口集合:"+portList);
    }
}
