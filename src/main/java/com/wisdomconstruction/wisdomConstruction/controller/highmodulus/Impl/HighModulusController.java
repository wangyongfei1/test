package com.wisdomconstruction.wisdomConstruction.controller.highmodulus.Impl;

import com.wisdomconstruction.wisdomConstruction.common.dto.highmodulus.ConstructionHighModulusDTO;
import com.wisdomconstruction.wisdomConstruction.controller.highmodulus.HighModulusApi;
import com.wisdomconstruction.wisdomConstruction.dao.base.domain.highmodulus.ConstructionHighModulusDO;
import com.wisdomconstruction.wisdomConstruction.service.highmodulus.HighModulusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HighModulusController implements HighModulusApi {
    @Autowired
    private HighModulusService highModulusService;
    /**
     * 获取高支模设备实时数据
     * @param  serialNumber
     * @return
     */
    public List<ConstructionHighModulusDTO> getHighModulus(@RequestParam String serialNumber) {
        return highModulusService.getHighModulus(serialNumber);
    }

    /**
     * 获取高支模设备报警实时数据
     * @param  serialNumber
     * @return
     */
    public List<ConstructionHighModulusDTO> getAlertHighModulus(@RequestParam String serialNumber) {
        return highModulusService.getAlertHighModulus(serialNumber);
    }

    /**
     * 获取当天报警次数
     * @param serialNumber
     * @return
     */
    public Integer getAlertCount(@RequestParam String serialNumber) {
        return highModulusService.getAlertCount(serialNumber);
    }
}