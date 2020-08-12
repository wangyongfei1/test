package com.wisdomconstruction.wisdomConstruction.tool;


import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
@Slf4j
public class CRCCheck {

    public static void main(String[] args) throws UnsupportedEncodingException {
        String str="Java生成CRC16数据校验码";
        byte[] data=str.getBytes();
        log.info(CRCCheck.getCRC(data));
    }

    /**
     * 计算产生校验码
     * @param data 需要校验的数据
     * @return 校验码
     */
    public static String Make_CRC(byte[] data) {
        byte[] buf = new byte[data.length];// 存储需要产生校验码的数据
        for (int i = 0; i < data.length; i++) {
            buf[i] = data[i];
        }
        int len = buf.length;
        int crc = 0xFFFF;//16位
        for (int pos = 0; pos < len; pos++) {
            if (buf[pos] < 0) {
                crc ^= (int) buf[pos] + 256; // XOR byte into least sig. byte of
                // crc
            } else {
                crc ^= (int) buf[pos]; // XOR byte into least sig. byte of crc
            }
            for (int i = 8; i != 0; i--) { // Loop over each bit
                if ((crc & 0x0001) != 0) { // If the LSB is set
                    crc >>= 1; // Shift right and XOR 0xA001
                    crc ^= 0xA001;
                } else
                    // Else LSB is not set
                    crc >>= 1; // Just shift right
            }
        }
        return Integer.toHexString(crc);
    }

    /**
     * 校验码
     */

    public static String getCRC(byte[] data){
        String c = Make_CRC(data);
        if (c.length() == 4) {
            c = c.substring(2, 4) + c.substring(0, 2);
        } else if (c.length() == 3) {
            c = "0" + c;
            c = c.substring(2, 4) + c.substring(0, 2);
        } else if (c.length() == 2) {
            c = "0" + c.substring(1, 2) + "0" + c.substring(0, 1);
        }
        return c;
    }

    /**
     * 校验码低位
     */

    public static String getLowCRC(byte[] data){
        String c = Make_CRC(data);
        if (c.length() == 4) {
            c = c.substring(0, 2);
        } else if (c.length() == 3) {
            c = "0" + c;
            c = c.substring(0, 2);
        } else if (c.length() == 2) {
            c ="0" + c.substring(0, 1);
        }
        return c;
    }

    /**
     * 校验码高位
     */

    public static String getHightCRC(byte[] data){
        String c = Make_CRC(data);
        if (c.length() == 4) {
            c = c.substring(2, 4);
        } else if (c.length() == 3) {
            c = "0" + c;
            c = c.substring(2, 4) ;
        } else if (c.length() == 2) {
            c = "0" + c.substring(1, 2);
        }
        return c;
    }
}
