package com.jjlapi.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jjlapi.jjlapicommon.model.entity.UserInterfaceInfo;
import com.jjlapi.project.common.ErrorCode;
import com.jjlapi.project.exception.BusinessException;
import com.jjlapi.project.mapper.UserInterfaceInfoMapper;
import com.jjlapi.project.service.UserInterfaceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
* @author jinjl
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service实现
* @createDate 2024-01-26 14:24:49
*/
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements UserInterfaceInfoService {

    @Override
    public void validUserInterfaceInfo(UserInterfaceInfo userinterfaceInfo, boolean add) {
        // 判断接口信息对象是否为空,为空则抛出参数错误的异常
        if (userinterfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 如果是添加操作
        if (add) {
            if (userinterfaceInfo.getInterfaceInfoId()<=0 || userinterfaceInfo.getUserId()<=0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR,"接口或用户不存在");
            }
        }
        if (userinterfaceInfo.getLeftNum()<0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "剩余次数不能小于0");
        }
    }

    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        // 判断
        if (interfaceInfoId <= 0 || userId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UpdateWrapper<UserInterfaceInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("interfaceInfoId", interfaceInfoId);
        updateWrapper.eq("userId", userId);

//        updateWrapper.gt("leftNum", 0);
        updateWrapper.setSql("leftNum = leftNum - 1, totalNum = totalNum + 1");
        return this.update(updateWrapper);
    }
//    @Override
//    @Transactional  // 添加事务注解
//    public boolean invokeCount(long interfaceInfoId, long userId) {
//        if (interfaceInfoId <= 0 || userId <= 0) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }
//
//        // 开启数据库事务
//        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//        TransactionStatus status = transactionManager.getTransaction(def);
//
//        try {
//            // 使用悲观锁来锁定要更新的记录，确保其他事务无法同时修改
//            UserInterfaceInfo userInterfaceInfo = this.selectForUpdate(interfaceInfoId, userId);
//
//            // 判断记录是否存在
//            if (userInterfaceInfo == null) {
//                throw new BusinessException(ErrorCode.PARAMS_ERROR);
//            }
//
//            // 更新操作
//            userInterfaceInfo.setLeftNum(userInterfaceInfo.getLeftNum() - 1);
//            userInterfaceInfo.setTotalNum(userInterfaceInfo.getTotalNum() + 1);
//            boolean updateResult = this.updateById(userInterfaceInfo);
//
//            // 提交事务
//            transactionManager.commit(status);
//
//            return updateResult;
//        } catch (Exception e) {
//            // 发生异常时回滚事务
//            transactionManager.rollback(status);
//            throw e;
//        }
//    }
//
//    private UserInterfaceInfo selectForUpdate(long interfaceInfoId, long userId) {
//        return getBaseMapper().selectOne(new LambdaQueryWrapper<UserInterfaceInfo>()
//                .eq(UserInterfaceInfo::getInterfaceInfoId, interfaceInfoId)
//                .eq(UserInterfaceInfo::getUserId, userId)
//                .last("FOR UPDATE"));
//    }
}




