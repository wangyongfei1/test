package com.wisdomconstruction.wisdomConstruction.controller.Impl;

import com.wisdomconstruction.wisdomConstruction.common.dto.dust.ConstructionSiteDustDTO;
import com.wisdomconstruction.wisdomConstruction.controller.DmodelApi;
import com.wisdomconstruction.wisdomConstruction.service.siteDust.SiteDustService;
import com.wisdomconstruction.wisdomConstruction.service.weight.WeightService;
import com.wisdomconstruction.wisdomConstruction.tool.ServiceSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Map;


@RestController
public class DmodelController implements DmodelApi {

    @Autowired
    private SiteDustService dustService;

    @Autowired
    private WeightService weightService;

    /**
     * 扬尘设备实时数据
     * @param  modelType  设备类型
     * @param  programNo  项目编号
     * @return
     */
    public List<ConstructionSiteDustDTO> dust(String modelType, String programNo) {
        return dustService.dust(modelType, programNo);
    }

    /**
     * 一周或一月的空气质量
     * @param projectNo 项目编号
     * @param isInWeek 是否是一周内
     * @return
     */
    public Map<?, ?> airQuality(String projectNo, Integer isInWeek) {
        return dustService.listAirQuality(projectNo, isInWeek);
    }

    /**
     * 测试
     * @return
     */
    public void test() {
        Socket socket = null;
        try {
            System.out.println("connecting...");
            //socket = new Socket("115.29.240.96", 2317);
            socket = new Socket("127.0.0.1", 2317);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * AT指令控制DTU
     *
     * @param at       指令
     * @param imei     设备imei
     * @return String
     */
    public String atInstruction(String at, String imei) {
        return dustService.atInstruction(at, imei);
    }

    /**
     * 地磅指令测试
     * @return
     */
    public String weight() {
        String hexString="02 41 41 42 03";
        String resultString = new ServiceSocket().send("860222043545184", hexString);
        System.out.println(resultString);
        return resultString;
    }

    /**
     * 地磅数据获取
     * @return
     */
    public String getWeight(String modelType, String programNo) {
        return weightService.getWeight(modelType, programNo);
    }
}


