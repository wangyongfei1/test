package com.wisdomconstruction.wisdomConstruction.tool;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
@Slf4j
public class zithread implements Runnable {
    private BufferedReader reader;

    private Socket socket;

    public zithread(Socket clientSocket)
    {
        log.info("-----------------触发监听:---------------------------");
        try
        {
            // 得到socket连接
            socket = clientSocket;
            // 得到客户端发来的消息 转成输入流
            //获取输入流，并读取客户端信息
            InputStreamReader isReader = new InputStreamReader(socket.getInputStream());//字符流

            reader = new BufferedReader(isReader);//缓冲方式文本读取由Socket对象得到输入流，并构造相应的BufferedReader对象

            //发送指令 输出流

         /*OutputStream os=socket.getOutputStream();

         PrintWriter pw=new PrintWriter(os);

         String info=" hello  kitty ";

         pw.write(info);

         pw.flush();*/

            //=socket.shutdownOutput();

        } catch (IOException e)

        {

            e.printStackTrace();

        }

    }
    /*监听端口数据处理*/
    public void run() {
        String message;
        try
        {


            while((message = reader.readLine()) != null)//readLine 方法读数据

            {

                log.info("客户端消息: " + message);


            }

        } catch (IOException e)

        {

            e.printStackTrace();

        }

    }
}
