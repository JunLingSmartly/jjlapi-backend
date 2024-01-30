package com.jjlapi.project.service;

import com.jjlapi.project.model.entity.UserInterfaceInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author jinjl
 * @description ��Ա�user_interface_info(�û����ýӿڹ�ϵ)�������ݿ����Service
 * @createDate 2024-01-26 14:24:49
 */
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {
    /**
     * У��
     *
     * @param userInterfaceInfo
     * @param add �Ƿ�Ϊ����У��
     */
    void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add);

    /**
     * ���ýӿ�ͳ��
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    boolean invokeCount(long interfaceInfoId,long userId);
}
