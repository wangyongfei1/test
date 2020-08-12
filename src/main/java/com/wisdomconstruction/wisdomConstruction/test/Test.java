package com.wisdomconstruction.wisdomConstruction.test;

import lombok.extern.slf4j.Slf4j;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

@Slf4j
public class Test {


//    public static void main(String[] args) {
//
//        byte[] b = new byte[8];
//        b[0] = (byte) 0x01;
//        b[1] = (byte) 0x03;
//        b[2] = (byte) 0x00;
//        b[3] = (byte) 0x00;
//        b[4] = (byte) 0x00;
//        b[5] = (byte) 0x02;
//        b[6] = (byte) 0xC4;
//        b[7] = (byte) 0x0B;
//        String instruction = TcpSendTool.sendInstruction("115.29.240.96", 2317, bytesToString(b,8));
//    }

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


        public static void main(String[] args) {
            int i = Integer.parseInt("01f5", 16);
            System.out.println(i);
           /* Socket socket = null;
            try {
                log.info("connecting...");
                //socket = new Socket("115.29.240.96", 2317);
                socket = new Socket("127.0.0.1", 2317);
                log.info("connection success");
                // 输入任意字符发送，输入q退出
                Scanner in = new Scanner(System.in);
                String str = "01 03 00 00 00 02 C4 0B"; //发送的16进制字符串
                byte[] bytes = hexStringToByteArray(str);
                OutputStream os = socket.getOutputStream();
                while (!(in.nextLine()).equals("q")) { //输入q退出
                    os.write(bytes);
                }
               //os.write("你好,服务端".getBytes());
                //os.close();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (socket != null) {
                    try {
                        //socket.close();
                    } catch (Exception e) {

                    }
                }
            }*/
        }

        /**
         * 16进制表示的字符串转换为字节数组
         *
         * @param hexString 16进制表示的字符串
         * @return byte[] 字节数组
         */
        public static byte[] hexStringToByteArray(String hexString) {
            hexString = hexString.replaceAll(" ", "");
            int len = hexString.length();
            byte[] bytes = new byte[len / 2];
            for (int i = 0; i < len; i += 2) {
                // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个字节
                bytes[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character
                        .digit(hexString.charAt(i + 1), 16));
            }
            return bytes;
        }



}
