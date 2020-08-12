package com.wisdomconstruction.wisdomConstruction.controller.highmodulusBasePoint.Impl;

import com.wisdomconstruction.wisdomConstruction.common.dto.highmodulusBasePoint.HighModulusBasePointDTO;
import com.wisdomconstruction.wisdomConstruction.controller.highmodulusBasePoint.HighModulusBasePointApi;
import com.wisdomconstruction.wisdomConstruction.service.highmodulusBasePoint.HighModulusBasePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HighModulusBasePointController implements HighModulusBasePointApi {

    @Autowired
    private HighModulusBasePointService highModulusBasePointService;
    /**
     * 获取高支模设备基准数据
     * @param serialNumber
     * @return
     */
    public HighModulusBasePointDTO getHighModulusBasePoint(@RequestParam String serialNumber) {
        return highModulusBasePointService.getHighModulusBasePoint(serialNumber);
    }

    /**
     * 根据项目编号获取高支模设备基准数据
     * @param projectNo
     * @return
     */
    public List<HighModulusBasePointDTO> getHighModulusBasePoints(@RequestParam String projectNo) {
        return highModulusBasePointService.getHighModulusBasePoints(projectNo);
    }

    /**
     * 添加高支模设备基准数据
     * @param highModulusBasePointDTO
     * @return
     */
    public boolean addHighModulusBasePoint(@RequestBody HighModulusBasePointDTO highModulusBasePointDTO){
        return highModulusBasePointService.addHighModulusBasePoint(highModulusBasePointDTO);
    }

    /**
     * 更新高支模基准点
     * @param serialNumber
     * @return
     */
    @Override
    public boolean updateBasePoint(String serialNumber) {
        return highModulusBasePointService.updateBasePoint(serialNumber);
    }
    /**
     * 设置高支模基准点
     * @param serialNumber
     * @return
     */
    @Override
    public boolean setBasePoint(String serialNumber) {
        return highModulusBasePointService.setBasePoint(serialNumber);
    }
}