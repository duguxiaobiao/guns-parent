package com.stylefeng.guns.api.cinema.model.vo;

import com.stylefeng.guns.api.base.BaseModel;
import lombok.Data;

/**
 * @author ztkj-hzb
 * @Date 2019/9/17 10:26
 * @Description 放映场次实体
 */
@Data
public class FieldVo extends BaseModel {

    /**
     * 放映场次id
     */
    private Integer fieldId;

    /**
     * 开始时间
     */
    private String beginTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 语言
     */
    private String language;

    /**
     * 影厅名称
     */
    private String hallName;

    /**
     * 价格
     */
    private String price;
}
