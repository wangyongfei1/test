package com.wisdomconstruction.wisdomConstruction.tool.wxmessagepush;

import cn.hutool.core.collection.CollectionUtil;
import com.wisdomconstruction.wisdomConstruction.common.dto.user.CompanyUserInfoDTO;
import com.wisdomconstruction.wisdomConstruction.dao.base.domain.company.CompanyProjectInfoDO;
import com.wisdomconstruction.wisdomConstruction.dao.base.domain.wxuser.WxUserInfoDO;
import com.wisdomconstruction.wisdomConstruction.dao.base.service.companyproject.CompanyProjectDAO;
import com.wisdomconstruction.wisdomConstruction.dao.base.service.wxuser.WxUserInfoDAO;
import com.wisdomconstruction.wisdomConstruction.service.user.UserService;
import com.wisdomconstruction.wisdomConstruction.tool.SpringBeanFactoryTool;
import com.wisdomconstruction.wisdomConstruction.tool.wxmessagepush.wx.TemplateData;
import org.apache.commons.lang3.ObjectUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangyongfei
 * @date 2020/7/9 16:33
 */
public class SendMessageUtils {


    private static final String TEMPLATE_ID = "j929hP8r0sdsBm26B4oCfoOvQtLtklSH14O93IlKA3E";

    public static void SendMessage(String projectNo,String deviceNo) {
        CompanyProjectDAO companyProjectDAO = (CompanyProjectDAO)SpringBeanFactoryTool.getBeanByName("companyProjectDAO");
        CompanyProjectInfoDO companyProjectInfoDO = companyProjectDAO.getCompanyProjectInfoDO(projectNo);
        WxUserInfoDAO wxUserInfoDAO = (WxUserInfoDAO) SpringBeanFactoryTool.getBeanByName("wxUserInfoDAO");
        UserService userService = (UserService)SpringBeanFactoryTool.getBeanByName("userService");
        if (ObjectUtils.isNotEmpty(companyProjectInfoDO) && ObjectUtils.isNotEmpty(companyProjectInfoDO.getCompanyNo())) {
            List<CompanyUserInfoDTO> allUserInfo = userService.getAllUserInfo(companyProjectInfoDO.getCompanyNo(), projectNo);
            if (CollectionUtil.isNotEmpty(allUserInfo)) {
                allUserInfo.forEach(companyUserInfo -> {
                    String unionId = companyUserInfo.getUnionId();
                    //获取openid
                    WxUserInfoDO wxUserInfo = wxUserInfoDAO.getWxUserInfo(unionId);
                    if (ObjectUtils.isNotEmpty(wxUserInfo) && ObjectUtils.isNotEmpty(wxUserInfo.getOpenId())) {
                        String openId = wxUserInfo.getOpenId();
                        //组装消息体
                        Map<String, Object> dataMap = new HashMap<>();

                        dataMap.put("first", new TemplateData("您好，您有一个新的报警消息。"));
                        dataMap.put("keyword1", new TemplateData(companyProjectInfoDO.getProjectName()));
                        dataMap.put("keyword2", new TemplateData("扬尘报警"));
                        dataMap.put("keyword3", new TemplateData("系统监控"));
                        dataMap.put("keyword4", new TemplateData(deviceNo));
                        dataMap.put("keyword5", new TemplateData(companyProjectInfoDO.getProjectAddress()));
                        dataMap.put("remark", new TemplateData("PM2.5或PM10超过阈值"));

                        WxUtils.wxPublicNumberMessgesPush(openId, TEMPLATE_ID, WxUtils.getWxPublicNumberAccessToken().getAccessToken(), dataMap, null);
                    }

                });
            }
        }
    }

    private static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }


}





