package com.jjlapi.project.model.dto.interfaceInfo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 接口调用请求
 *
 * @TableName product
 */
@Data
public class InterfaceInfoInvokeRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    /**
     * 请求参数
     */
    private String requestParams;
    private String userRequestParams;
}