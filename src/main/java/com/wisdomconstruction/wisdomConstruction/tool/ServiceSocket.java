package com.wisdomconstruction.wisdomConstruction.tool;

/**
 * @Author: zhouhe
 * @Date: 2019/4/8 9:30
 */
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.wisdomconstruction.wisdomConstruction.common.code.BizCodeMsgEnum;
import com.wisdomconstruction.wisdomConstruction.common.dto.highmodulus.ConstructionHighModulusDTO;
import com.wisdomconstruction.wisdomConstruction.common.exception.ConstructionBizException;
import com.wisdomconstruction.wisdomConstruction.dao.base.domain.equipment.ConstructionEquipmentDO;
import com.wisdomconstruction.wisdomConstruction.dao.base.enums.equipment.ConstructionEquipmentDOPropertiesEnum;
import com.wisdomconstruction.wisdomConstruction.dao.base.service.equipment.ConstructionEquipmentDAO;
import com.wisdomconstruction.wisdomConstruction.enums.EquipmentEnum;
import com.wisdomconstruction.wisdomConstruction.service.highmodulus.HighModulusService;
import com.wisdomconstruction.wisdomConstruction.tool.socket.ClientSocket;
import com.wisdomconstruction.wisdomConstruction.tool.socket.DelongServerSocket;
import lombok.extern.slf4j.Slf4j;
import java.io.*;

/**
 * 服务端
 * 负责发送数据
 */

@Slf4j
public class ServiceSocket{

    /**
     * 通过DTU下发指令获取智慧工地硬件设备数据
     *
     * @param dtuImei     设备imei
     * @param hexString   指令
     */
    public String send(String dtuImei,String hexString){
        try {
            //根据IMEI获取socket
            String IMEI = dtuImei;
            ClientSocket clientSocket = DelongServerSocket.clientsMap.get(IMEI);
            if (clientSocket==null){
                log.error("设备未连接..........");
            }
            OutputStream bw = clientSocket.getOutputStream();
            bw.write(hexStrToBinaryStr(hexString));
            bw.flush();
            System.out.println("发送数据完成...");
            if (EquipmentEnum.SPRAY.getDeviceType().equals(String.valueOf(clientSocket.getType()))){
                Thread.sleep(2000);
            }
            InputStream inputStream = clientSocket.getInputStream();
            if (inputStream.available() <= 0) {
                return null;
            }
            byte[] da = new byte[100];
            inputStream.read(da, 0, da.length);
            System.out.println("客户端："+bytesToString(da,da.length));
            return bytesToString(da,da.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取DTU的IMEI码
     */
    public static String  acquire(ClientSocket client) {
        DataOutputStream out = null;
        DataInputStream inp = null;
        byte[] data = new byte[1024];
        try {
            out = client.getOutputStream();
            inp = client.getInputStream();
            log.info("获取IMEI码...");
            String sn = "www.usr.cn#AT+IMEI";
            sn = strTo16(sn) + " 0D";
            out.write(hexStrToBinaryStr(sn));
            out.flush();
            inp.read(data, 0, data.length);
            System.out.println("IMEI码是："+new String(data,0,data.length));
            return new String(data, 0, data.length).split(":")[1].trim();
        } catch (Exception e) {
            return addHighModulus(data);
        }
    }

    /**
     * 保存高支模數據
     * @return
     */
    public static String addHighModulus(byte[] data){
        //SN码
        byte[] bs = new byte[12];
        System.arraycopy(data, 4, bs, 0, 12);
        String sn = new String(bs);
        if (sn.length()!=12){
            throw new ConstructionBizException(BizCodeMsgEnum.GET_MESSAGE_ERROR);
        }
        //立杆倾角
        byte[] polingAngle = new byte[4];
        System.arraycopy(data, 38, polingAngle, 0, 4);
        float polingAngleResult = byteArrayToFloat(polingAngle);
        String polingAngleStr = String.valueOf(polingAngleResult);

        //水平杆倾角
        byte[] horizontalAngle = new byte[4];
        System.arraycopy(data, 42, horizontalAngle, 0, 4);
        float horizontalAngleResult = byteArrayToFloat(horizontalAngle);
        String horizontalAngleStr = String.valueOf(horizontalAngleResult);

        //承重
        byte[] bearing = new byte[4];
        System.arraycopy(data, 46, bearing, 0, 4);
        float bearingResult = byteArrayToFloat(bearing);
        String bearingStr = String.valueOf(bearingResult);

        ConstructionHighModulusDTO constructionHighModulusDTO = new ConstructionHighModulusDTO();
        constructionHighModulusDTO.setPolingAngle(polingAngleStr);
        constructionHighModulusDTO.setHorizontalAngle(horizontalAngleStr);
        constructionHighModulusDTO.setBearing(bearingStr);
        constructionHighModulusDTO.setSerialNumber(sn);
        HighModulusService highModulusService = (HighModulusService) SpringBeanFactoryTool.getBeanByName("highModulusService");
        highModulusService.addHighModulus(constructionHighModulusDTO);
        return sn;
    }

    /**
     * 获取设备类型
     * @return
     */
    public static Integer getDeviceType(String IMEI){
        ConstructionEquipmentDAO constructionEquipmentDAO = (ConstructionEquipmentDAO) SpringBeanFactoryTool.getBeanByName("constructionEquipmentDAOImpl");
        ConstructionEquipmentDO constructionEquipmentDO = constructionEquipmentDAO.selectOne(new EntityWrapper<ConstructionEquipmentDO>().eq(ConstructionEquipmentDOPropertiesEnum.DTU_IMEI.getColumn(), IMEI));
        if (constructionEquipmentDO!=null){
            return constructionEquipmentDO.getDeviceType();
        }
        log.info("设备类型不存在");
        return null;
    }

    /**
     * 通过DTU下发指令配置DTU参数
     *
     * @param dtuImei     设备imei
     * @param hexString   指令
     */
    public String sendDTU(String dtuImei,String hexString){
        try {
            //根据IMEI获取socket
            String IMEI = dtuImei;
            ClientSocket clientSocket = DelongServerSocket.clientsMap.get(IMEI);
            if (clientSocket==null){
                log.error("设备未连接..........");
            }
            OutputStream bw = clientSocket.getOutputStream();
            bw.write(hexStrToBinaryStr(strTo16(hexString) + " 0D"));
            bw.flush();
            System.out.println("发送数据完成...");
            InputStream inputStream = clientSocket.getInputStream();
            if (inputStream.available()<=0){
                log.info("返回数据为空");
                return null;
            }
            byte[] da = new byte[100];
            inputStream.read(da, 0, da.length);
            String result = new String(da,0,da.length);
            System.out.println("返回数据是："+result);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //字符串转16进制
    public static String strTo16(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str +" "+ s4;
        }
        return str;
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

    /**
     * 将十六进制的字符串转换成字节数组
     *
     * @param hexString
     * @return
     */
    public static byte[] hexStrToBinaryStr(String hexString) {
        hexString = hexString.replaceAll(" ", "");
        int len = hexString.length();
        int index = 0;
        byte[] bytes = new byte[len / 2];
        while (index < len) {
            String sub = hexString.substring(index, index + 2);
            bytes[index/2] = (byte)Integer.parseInt(sub,16);
            index += 2;
        }
        return bytes;
    }

    /**
     * 将四字节的16进制转换为float
     * @param arg
     * @return
     */
    public static float byteArrayToFloat(byte[] arg) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(arg);
        DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
        float result = 0.0f;
        try {
            return dataInputStream.readFloat();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return result;
    }

    /**
     * 将字节数组转化为int
     * @param b
     * @return
     */
    public static int byteArrayToInt(byte[] b){
        byte[] a = new byte[4];
        int i = a.length - 1,j = b.length - 1;
        for (; i >= 0 ; i--,j--) {//从b的尾部(即int值的低位)开始copy数据
            if(j >= 0)
                a[i] = b[j];
            else
                a[i] = 0;//如果b.length不足4,则将高位补0
        }
        int v0 = (a[0] & 0xff) << 24;//&0xff将byte值无差异转成int,避免Java自动类型提升后,会保留高位的符号位
        int v1 = (a[1] & 0xff) << 16;
        int v2 = (a[2] & 0xff) << 8;
        int v3 = (a[3] & 0xff) ;
        return v0 + v1 + v2 + v3;
    }

    /**
     * 将int转为二进制字符串
     * @param num
     * @return
     */
    public static String convert10To2(int num) {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        // 正整数
        if (num > 0) {
            while (num != 0) {
                sb.append(num % 2);
                num = num / 2;
            }
            sb.reverse();
            return sb.toString();
            // 负整数
        } else if (num < 0) {
            //负数先换成正数
            num = -num;
            while (num != 0) {
                //取反
                sb.append((num % 2 == 0) ? 1 : 0);
                num = num / 2;
            }
            // 进位
            int carry = 0;
            for (int i = 0; i < sb.length(); i++) {
                // 以下注意，(Integer.valueOf(sb.charAt(i))为字符'0''1'的ASCII值48 49，需转成字符串
                //+1
                if (i == 0) {
                    if (Integer.valueOf(sb.charAt(i) + "") + 1 == 2) {
                        sb2.append(0);
                        carry = 1;
                    } else if (Integer.valueOf(sb.charAt(i) + "") + 1 == 1) {
                        sb2.append(1);
                        carry = 0;
                    }
                } else {
                    if (Integer.valueOf(sb.charAt(i) + "") + carry == 2) {
                        sb2.append(0);
                        carry = 1;
                    } else if (Integer.valueOf(sb.charAt(i) + "") + carry == 1) {
                        sb2.append(1);
                        carry = 0;
                    } else if (Integer.valueOf(sb.charAt(i) + "") + carry == 0) {
                        sb2.append(0);
                        carry = 0;
                    }
                }
            }
            sb2.reverse();
            return sb2.toString();
            // 整数0
        } else {
            return "0";
        }
    }

    public static String formatResult(String str) {
        StringBuilder sb = new StringBuilder("");
        if (str.length() != 10) {
            int num = 10 - str.length();
            for (int i = 0; i < num; i++) {
                sb.append("0");
            }
        }
        sb.append(str);
        return sb.toString();
    }

    public static String analyseResult(String str){
        StringBuilder sb = new StringBuilder("");
        if("0000000000".equals(str)){
            return "0";
        }
        char[] chars = str.toCharArray();
        for (int i = chars.length - 1; i >= 0; i--) {
            if (chars[i] == '1') {
                int count = 10 - i;
                if (i == 0) {
                    sb.append("A");
                } else {
                    sb.append(String.valueOf(count) + ",");
                }
            }
        }
        String result = sb.toString();
        if(result.endsWith(",")){
            result = result.substring(0,result.length() - 1);
        }
        return result;
    }

}
