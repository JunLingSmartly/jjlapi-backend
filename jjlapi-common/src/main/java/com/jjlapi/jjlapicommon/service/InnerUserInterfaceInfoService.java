package com.jjlapi.jjlapicommon.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jjlapi.jjlapicommon.model.entity.InterfaceInfo;
import com.jjlapi.jjlapicommon.model.entity.User;
import com.jjlapi.jjlapicommon.model.entity.UserInterfaceInfo;

/**
 * @author jinjl
 * @description ????user_interface_info(???????????)????????????Service
 * @createDate 2024-01-26 14:24:49
 */
public interface InnerUserInterfaceInfoService{

    /**
     * 调用接口统计
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    boolean invokeCount(long interfaceInfoId,long userId);
}
