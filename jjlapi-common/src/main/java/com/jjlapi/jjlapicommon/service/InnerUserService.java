package com.jjlapi.jjlapicommon.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jjlapi.jjlapicommon.model.entity.User;

/**
 * 用户服务
 *
 * @author jjlpi
 */
public interface InnerUserService {

    /**
     * 数据库中查看是否已分配给用户密钥（accessKey）
     */
    User getInvokeUser(String accessKey);
}
