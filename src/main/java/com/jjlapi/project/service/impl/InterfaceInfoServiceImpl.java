package com.jjlapi.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jjlapi.project.common.ErrorCode;
import com.jjlapi.project.exception.BusinessException;
import com.jjlapi.jjlapicommon.model.entity.InterfaceInfo;
import com.jjlapi.project.service.InterfaceInfoService;
import com.jjlapi.project.mapper.InterfaceInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
* @author jinjl
* @description 针对表【interface_info(接口信息)】的数据库操作Service实现
* @createDate 2024-01-05 11:55:08
*/
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
    implements InterfaceInfoService{

    @Override
    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add) {
        // 判断接口信息对象是否为空,为空则抛出参数错误的异常
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 获取接口信息对象的名称
        String name = interfaceInfo.getName();

        // 如果是添加操作,所有参数必须非空,否则抛出参数错误的异常
        if (add) {
            if (StringUtils.isAnyBlank(name)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
        // 如果接口名称不为空且长度大于50,抛出参数错误的异常,错误信息为"名称过长"
        // 本期写成<50,(没有解决),第二期视频中解决了
        if (StringUtils.isNotBlank(name) && name.length() > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "名称过长");
        }
    }

}




