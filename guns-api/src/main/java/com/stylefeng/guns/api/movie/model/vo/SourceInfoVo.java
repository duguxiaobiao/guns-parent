package com.stylefeng.guns.api.movie.model.vo;

import com.stylefeng.guns.api.base.BaseModel;
import lombok.Data;

/**
 * @author ztkj-hzb
 * @Date 2019/8/30 15:07
 * @Description 影片来源响应实体
 */
@Data
public class SourceInfoVo extends BaseModel {

    /**
     * 来源id
     */
    private String sourceId;

    /**
     * 来源名称
     */
    private String sourceName;

    /**
     * 是否激活
     */
    private boolean isActive;

}
