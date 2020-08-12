package com.wisdomconstruction.wisdomConstruction.service.weight.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.wisdomconstruction.wisdomConstruction.common.code.BizCodeMsgEnum;
import com.wisdomconstruction.wisdomConstruction.common.dto.goods.ConstructionGoodsDTO;
import com.wisdomconstruction.wisdomConstruction.common.dto.weight.ConstructionWeightDTO;
import com.wisdomconstruction.wisdomConstruction.common.exception.ConstructionBizException;
import com.wisdomconstruction.wisdomConstruction.common.vo.weight.ConstructionWeightVO;
import com.wisdomconstruction.wisdomConstruction.dao.base.domain.equipment.ConstructionEquipmentDO;
import com.wisdomconstruction.wisdomConstruction.dao.base.domain.goods.ConstructionGoodsDO;
import com.wisdomconstruction.wisdomConstruction.dao.base.domain.weight.ConstructionWeightDO;
import com.wisdomconstruction.wisdomConstruction.dao.base.enums.equipment.ConstructionEquipmentDOPropertiesEnum;
import com.wisdomconstruction.wisdomConstruction.dao.base.enums.goods.ConstructionGoodsDOPropertiesEnum;
import com.wisdomconstruction.wisdomConstruction.dao.base.enums.weight.ConstructionWeightDOPropertiesEnum;
import com.wisdomconstruction.wisdomConstruction.dao.base.service.equipment.ConstructionEquipmentDAO;
import com.wisdomconstruction.wisdomConstruction.dao.base.service.goods.ConstructionGoodsDAO;
import com.wisdomconstruction.wisdomConstruction.dao.base.service.weight.ConstructionWeightDAO;
import com.wisdomconstruction.wisdomConstruction.service.weight.WeightService;
import com.wisdomconstruction.wisdomConstruction.tool.ServiceSocket;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class WeightServiceImpl implements WeightService {
    @Autowired
    private ConstructionEquipmentDAO constructionEquipmentDAO;
    @Autowired
    private ConstructionWeightDAO constructionWeightDAO;
    @Autowired
    private ConstructionGoodsDAO constructionGoodsDAO;

    @Override
    public String getWeight(String modelType, String programNo) {
        //指令(获取当前称重)
        String hexString="02 41 41 42 03";
        //查询设备参数
        ConstructionEquipmentDO constructionEquipmentDO = constructionEquipmentDAO.selectOne(new EntityWrapper<ConstructionEquipmentDO>().eq(ConstructionEquipmentDOPropertiesEnum.DEVICE_TYPE.getColumn(), Integer.valueOf(modelType))
                .eq(ConstructionEquipmentDOPropertiesEnum.PROJECT_NO.getColumn(), programNo));
        if (constructionEquipmentDO!=null){
            String resultString = new ServiceSocket().send(constructionEquipmentDO.getDtuImei(), hexString);
            if (StringUtils.isNotEmpty(resultString)){
                //解析数据
                return analysisData(resultString);
            }
            return "设备未连接";
        }
        return "设备不存在";
    }

    /**
     * 添加称重信息（毛重）
     * @param constructionWeightDTO
     * @return
     */
    @Override
    public boolean addWeight(ConstructionWeightDTO constructionWeightDTO) {
        ConstructionWeightDO constructionWeightDO = new ConstructionWeightDO();
        BeanUtils.copyProperties(constructionWeightDTO,constructionWeightDO);
        String serialNumber = generateSerialNumber(constructionWeightDTO.getLicensePlateNo());
        constructionWeightDO.setSerialNumber(serialNumber);
        constructionWeightDO.setCreatorInfo();
        constructionWeightDO.setCargoSkinWeight(0f);
        constructionWeightDO.setCargoNetWeight(0f);
        constructionWeightDO.setInTime(constructionWeightDO.getGmtCreate());
        constructionWeightDO.setIsDelete(0);
        return constructionWeightDAO.insert(constructionWeightDO);
    }

    /**
     * 更新称重信息（皮重）
     * @param constructionWeightDTO
     * @return
     */
    @Override
    public boolean updateWeight(ConstructionWeightDTO constructionWeightDTO) {
        ConstructionWeightDO constructionWeightDO = new ConstructionWeightDO();
        BeanUtils.copyProperties(constructionWeightDTO, constructionWeightDO);
        ConstructionWeightDTO constructionWeightDTO1 = constructionWeightDAO.selectLastByLicensePlateNo(constructionWeightDTO.getLicensePlateNo());
        if(constructionWeightDTO1!=null){
            BeanUtils.copyProperties(constructionWeightDTO1, constructionWeightDO);
            Wrapper<ConstructionWeightDO> wrapper = new EntityWrapper<>();
            wrapper.eq(ConstructionWeightDOPropertiesEnum.SERIAL_NUMBER.getColumn(), constructionWeightDTO1.getSerialNumber());
            constructionWeightDO.setCargoSkinWeight(constructionWeightDTO.getCargoSkinWeight());
            constructionWeightDO.setCargoNetWeight(constructionWeightDTO1.getCargoGrossWeight() - constructionWeightDTO.getCargoSkinWeight());
            constructionWeightDO.setOperator(constructionWeightDTO.getOperator());
            return constructionWeightDAO.update(constructionWeightDO, wrapper);
        }
        log.info("获取毛重信息失败！");
       return false;
    }

    /**
     * 获取地磅称重信息
     * @param modelType
     * @param projectNo
     * @return
     */
    @Override
    public List<ConstructionWeightDTO> getWeights(Integer modelType,String projectNo) {
        List<ConstructionWeightDTO> list = new ArrayList<>();
        ConstructionEquipmentDO constructionEquipmentDO = this.getConstructionEquipment(modelType, projectNo);
        if (constructionEquipmentDO!=null) {
            List<ConstructionWeightDTO> constructionWeightDTOS = constructionWeightDAO.selectWeightList(constructionEquipmentDO.getDtuImei());
            if(constructionWeightDTOS.size()>0){
                constructionWeightDTOS.stream().forEach(c->{
                    list.add(c);
                });
                return list;
            }
        }
        return list;
    }

    /**
     * 获取当日净重
     * @param modelType
     * @param projectNo
     * @return
     */
    @Override
    public float getTodayNetWeight(Integer modelType, String projectNo) {
        float weight = 0;
        ConstructionEquipmentDO constructionEquipmentDO = this.getConstructionEquipment(modelType, projectNo);
        if (constructionEquipmentDO!=null) {
            List<ConstructionWeightDO> constructionWeightDOS = constructionWeightDAO.getTodayWeight(constructionEquipmentDO.getDtuImei());
            if (constructionWeightDOS.size() > 0) {
                for(ConstructionWeightDO constructionWeightDO : constructionWeightDOS){
                    weight += constructionWeightDO.getCargoNetWeight();
                }
            }
            return weight;
        }
        log.info("获取当日称重记录失败！");
        return -1f;
    }

    /**
     * 获取当日打单次数
     * @param modelType
     * @param projectNo
     * @return
     */
    @Override
    public Integer getTodayBillCount(Integer modelType, String projectNo) {
        ConstructionEquipmentDO constructionEquipmentDO = this.getConstructionEquipment(modelType, projectNo);
        if(constructionEquipmentDO != null) {
            return constructionWeightDAO.getTodayBillCount(constructionEquipmentDO.getDtuImei());
        }
        log.info("获取地磅设备信息失败！");
        return -1;
    }

    /**
     * 添加货物或者供货商
     *
     * @param goodsDTO
     * @return
     */
    @Override
    public boolean addGoods(ConstructionGoodsDTO goodsDTO) {
        ConstructionGoodsDO constructionGoodsDO = new ConstructionGoodsDO();
        BeanUtils.copyProperties(goodsDTO, constructionGoodsDO);
        return constructionGoodsDAO.insert(constructionGoodsDO);
    }

    /**
     * 根据类型查询名称
     *
     * @param goodsType
     * @return
     */
    @Override
    public List<ConstructionGoodsDTO> selectNameList(Integer goodsType) {
        List<ConstructionGoodsDTO> constructionGoodsDTOS = new ArrayList<>();
        Collection collection = constructionGoodsDAO.selectList(new EntityWrapper<ConstructionGoodsDO>()
                .eq(StringUtils.isNotEmpty(String.valueOf(goodsType)), ConstructionGoodsDOPropertiesEnum.GOODS_TYPE.getColumn(), goodsType));
        if (!collection.isEmpty()) {
            collection.stream()
                    .forEach(constructionGoodsDO -> {
                        ConstructionGoodsDTO constructionGoodsDTO = new ConstructionGoodsDTO();
                        BeanUtils.copyProperties(constructionGoodsDO, constructionGoodsDTO);
                        constructionGoodsDTOS.add(constructionGoodsDTO);
                    });
        }
        return constructionGoodsDTOS;
    }

    /**
     * 获取当日操作员人数
     * @param modelType
     * @param projectNo
     * @return
     */
    @Override
    public Integer getTodayOperatorCount(Integer modelType, String projectNo) {
        ConstructionEquipmentDO constructionEquipmentDO = this.getConstructionEquipment(modelType, projectNo);
        if(constructionEquipmentDO!=null){
            return constructionWeightDAO.getTodayOperatorCount(constructionEquipmentDO.getDtuImei());
        }
        log.info("获取地磅设备信息失败！");
        return -1;
    }

    /**
     * 条件查询称重记录
     * @param constructionWeightVO
     * @return
     */
    @Override
    public List<ConstructionWeightDTO> queryWeightByCondition(ConstructionWeightVO constructionWeightVO) {
        List<ConstructionWeightDTO> constructionWeightDTOS = new ArrayList<>();
        ConstructionEquipmentDO constructionEquipmentDO = this.getConstructionEquipment(constructionWeightVO.getDeviceType(), constructionWeightVO.getProjectNo());
        if(constructionEquipmentDO !=null){
            constructionWeightVO.setDeviceNo(constructionEquipmentDO.getDtuImei());
            return constructionWeightDAO.queryWeightByCondition(constructionWeightVO);
        }
        return constructionWeightDTOS;
    }

    /**
     * 根据车牌号查询最新的一条称重记录
     * @param licensePlateNo
     * @return
     */
    @Override
    public ConstructionWeightDTO selectLastByLicensePlateNo(String licensePlateNo) {
        return constructionWeightDAO.selectLastByLicensePlateNo(licensePlateNo);
    }


    /**
     * 解析地磅数据
     * @return
     */
   private String analysisData(String resultString){
       try {
           log.info("地磅返回数据:{}", resultString);
           resultString = resultString.replace(" ", "");
           //净重
           String substring = resultString.substring(8, 20);
           String weight = new String(hexStrToBinaryStr(substring));
           String s = subString(weight);
           Integer.parseInt(s);
           return subString(weight);
       } catch (Exception e) {
           log.info("地磅数据解析失败,原因{}",e.getMessage());
       }
       return "0";
   }

    /**
     * 去掉字符串前面的零
     * @return
     */
    private   String subString(String str){
        int len = str.length();// 取得字符串的长度
        int index = 0;// 预定义第一个非零字符串的位置
        int count = 0;//是否有非零字符串
        char strs[] = str.toCharArray();// 将字符串转化成字符数组
        for (int i = 0; i < len; i++) {
            if ('0' != strs[i]) {
                index = i;// 找到非零字符串并跳出
                count++;
                break;
            }
        }
        String strLast = str.substring(index, len);// 截取字符串
        if (count==1){
            //有非零字符串
            return strLast;
        }
        return "0";
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
     * 生成流水号
     * @param string
     * @return
     */
    public static String generateSerialNumber(String string) {
        StringBuffer unicode = new StringBuffer();
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            unicode.append(Integer.toUnsignedLong(c));
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = sdf.format(new Date());
        return unicode.toString()+timestamp;
    }

    /**
     * 根据设备类型和项目编号获取设备
     * @param modelType
     * @param projectNo
     * @return
     */
    public ConstructionEquipmentDO getConstructionEquipment(Integer modelType,String projectNo){
       return constructionEquipmentDAO.selectOne(
                new EntityWrapper<ConstructionEquipmentDO>().eq(ConstructionEquipmentDOPropertiesEnum.DEVICE_TYPE.getColumn(), modelType)
                        .eq(ConstructionEquipmentDOPropertiesEnum.PROJECT_NO.getColumn(), projectNo));
    }
}
