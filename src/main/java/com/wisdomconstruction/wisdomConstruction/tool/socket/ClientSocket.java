package com.wisdomconstruction.wisdomConstruction.tool.socket;

import com.wisdomconstruction.wisdomConstruction.enums.EquipmentEnum;
import com.wisdomconstruction.wisdomConstruction.tool.ServiceSocket;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Data
@Slf4j
public class ClientSocket implements Runnable {
    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private String key;
    private Integer type;

    /**
     * 注册socket到map里
     *
     * @param socket
     * @return
     */
    public static ClientSocket register(Socket socket) {
        ClientSocket client = new ClientSocket();
        try {
            socket.setOOBInline(true);
            client.setSocket(socket);
            client.setInputStream(new DataInputStream(socket.getInputStream()));
            client.setOutputStream(new DataOutputStream(socket.getOutputStream()));
            log.info("获取设备编号..........");
            String key = ServiceSocket.acquire(client);
            //获取设备类型
            Integer type = ServiceSocket.getDeviceType(key);
            if (DelongServerSocket.clientsMap.containsKey(key)){
                //关闭重复客户端
                DelongServerSocket.clientsMap.get(key).logout();
            }
            client.setKey(key);
            client.setType(type);
            DelongServerSocket.clientsMap.put(client.getKey(), client);
            log.info("客户端集合:{}", DelongServerSocket.clientsMap);
            return client;
        } catch (Exception e) {
            log.info("客户端连接错误,错误原因{},登出.........", e.getMessage());
            client.logout();
        }
        return null;
    }

    /**
     * 发送数据
     *
     * @param str
     */
    public void send(String str) {
        try {
            outputStream.write(str.getBytes());
        } catch (IOException e) {
            logout();
        }
    }

    /**
     * 接收数据
     *
     * @return
     * @throws IOException
     */
    public String receive() {
        try {
            byte[] bytes = new byte[1024];
            //判断客户端是否有消息
            if (inputStream == null || inputStream.available() <= 0) {
                return null;
            }
            if (EquipmentEnum.MONITORING.getDeviceType().equals(String.valueOf(type))) {
                //高支模
                inputStream.read(bytes, 0, bytes.length);
                if (bytes[0] != 0) {
                    ServiceSocket.addHighModulus(bytes);
                    log.info("key:{},客户端信息:{}", key, Arrays.toString(bytes));
                }
            } else if (EquipmentEnum.WEIGHT.getDeviceType().equals(String.valueOf(type)) || EquipmentEnum.SPRAY.getDeviceType().equals(String.valueOf(type))) {
                //地磅喷淋
            } else {
                inputStream.read(bytes);
                String info = new String(bytes, "utf-8");
                log.info("key:{},客户端信息:{}", key, info);
            }
            return null;
        } catch (Exception e) {
            logout();
        }
        return null;
    }

    /**
     * 登出操作, 关闭各种流
     */
    public void logout() {
        if (key != null && DelongServerSocket.clientsMap.containsKey(key)) {
            DelongServerSocket.clientsMap.remove(key);
        }

        log.info("客户端集合:{}", DelongServerSocket.clientsMap);
        try {
            socket.shutdownOutput();
            socket.shutdownInput();
        } catch (IOException e) {
            e.getMessage();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.getMessage();
            }
        }
    }

    /**
     * 发送数据包, 判断数据连接状态
     *
     * @return
     */
    public boolean isSocketClosed() {
        try {
            socket.sendUrgentData(0x00);
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    @Override
    public void run() {
        // 每过5秒连接一次客户端
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(60*30);
                //读取客户端数据
                receive();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (isSocketClosed() && !socket.isClosed()) {
                log.info("客户端关闭.......");
                logout();
                break;
            }
            if (socket.isClosed()) {
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "Client{" +
                "socket=" + socket +
                ", inputStream=" + inputStream +
                ", outputStream=" + outputStream +
                ", key='" + key + '\'' +
                '}';
    }
}
