package com.stylefeng.guns.api.cinema.model.vo;

import com.stylefeng.guns.api.base.BaseModel;
import lombok.Data;

/**
 * @author ztkj-hzb
 * @Date 2019/9/26 16:49
 * @Description
 */
@Data
public class OrderQueryVo extends BaseModel {

    /**
     * 放映场次id
     */
    private Integer fieldId;

    /**
     * 开始时间
     */
    private String cinemaId;

    /**
     * 结束时间
     */
    private String filmId;


    /**
     * 价格
     */
    private String price;

}
