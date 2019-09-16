package com.stylefeng.guns.api.movie.model.vo;

import com.stylefeng.guns.api.base.BaseModel;
import lombok.Data;

/**
 * @author ztkj-hzb
 * @Date 2019/8/30 15:09
 * @Description 影片年代响应实体
 */
@Data
public class YearInfoVo extends BaseModel {

    /**
     * 年代id
     */
    private String yearId;

    /**
     * 年代名称
     */
    private String yearName;

    /**
     * 是否激活
     */
    private boolean isActive;

}
