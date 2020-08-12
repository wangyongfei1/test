package com.wisdomconstruction.wisdomConstruction.tool.socket;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Data
@Component
public class DelongServerSocket {

    @Value("${socket.port}")
    private Integer port;
    private boolean started;
    private ServerSocket serverSocket;
    public static ConcurrentHashMap<String, ClientSocket> clientsMap = new ConcurrentHashMap<>();
    private ExecutorService executorService = Executors.newFixedThreadPool(30);
    public void start() {
        start(null);
    }
    public void start(Integer port) {
        try {
            serverSocket = new ServerSocket(port == null ? this.port : port);
            started = true;
            log.info("端口已开启,占用{}端口号....",this.port);
        } catch (Exception e) {
            log.info("端口使用中....");
            log.info("请关掉相关程序并重新运行服务器！");
            e.printStackTrace();
            System.exit(0);
        }

        try {
            while (started) {
                Socket socket = serverSocket.accept();
                socket.setKeepAlive(true);
                ClientSocket register = ClientSocket.register(socket);
                if (register != null) {
                    log.info("客户端:"+socket.getInetAddress().getLocalHost()+"已连接到服务器");
                    executorService.submit(register);
                    ThreadPoolExecutor executorService = (ThreadPoolExecutor) this.executorService;
                    int poolSize = executorService.getPoolSize();
                    int activeCount = executorService.getActiveCount();
                    log.info("线程池线程数;{},线程池活跃线程数:{}",poolSize,activeCount);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
