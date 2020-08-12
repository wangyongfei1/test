package com.wisdomconstruction.wisdomConstruction.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 是否删除枚举
 *
 * @author 20314@etransfar.com(zhangbin)
 * @CreateDate 2019/8/2 16:21
 */
@Getter
@AllArgsConstructor
public enum IsDeleteEnum {
    /**
     * 未删除
     */
    NO(0),
    /**
     * 已删除
     */
    YES(1);

    /**
     * 删除状态
     */
    private Integer deleteStatus;
}
