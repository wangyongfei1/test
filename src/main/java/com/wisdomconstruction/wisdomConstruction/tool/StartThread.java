package com.wisdomconstruction.wisdomConstruction.tool;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
@Slf4j
public class StartThread extends Thread {

    public static Socket socket = null;

    public void run() {
        log.info("-----------------触发监听:---------------------------");
        int port = 2317;
        log.info("-----------------Tomcat port:" + port + "---------------------------");
        try {
          // 轮流等待请求
            while (true) {
                log.info("开始监听...");
                // 等待客户端请求,无请求则闲置;有请求到来时,返回一个对该请求的socket连接
                ServerSocket serverSocket = (ServerSocket)SpringBeanFactoryTool.getBeanByName("serverSocket");
                socket = serverSocket.accept();
                log.info("客户端:"+socket.getInetAddress().getLocalHost()+"已连接到服务器");
                //2、调用accept()方法开始监听，等待客户端的连接
                // 使用accept()阻塞等待客户请求，有客户
                //请求到来则产生一个Socket对象，并继续执行
                // 创建zithread对象,通过socket连接通信
                Thread t = new Thread(new zithread(socket));
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
