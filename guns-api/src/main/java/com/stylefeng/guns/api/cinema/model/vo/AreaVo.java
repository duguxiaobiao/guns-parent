package com.stylefeng.guns.api.cinema.model.vo;

import com.stylefeng.guns.api.base.BaseModel;
import lombok.Data;

/**
 * @author ztkj-hzb
 * @Date 2019/9/16 17:32
 * @Description 影院区域响应实体
 */
@Data
public class AreaVo extends BaseModel {

    /**
     * 区域id
     */
    private Integer areaId;

    /**
     * 区域名称
     */
    private String areaName;

    /**
     * 是否选中
     */
    private boolean isActive;

}
