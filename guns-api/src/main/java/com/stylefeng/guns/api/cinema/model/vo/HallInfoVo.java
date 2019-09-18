package com.stylefeng.guns.api.cinema.model.vo;

import com.stylefeng.guns.api.base.BaseModel;
import lombok.Data;

/**
 * @author ztkj-hzb
 * @Date 2019/9/17 11:08
 * @Description 放映厅响应实体
 */
@Data
public class HallInfoVo extends BaseModel {

    /**
     * 影厅场次id
     */
    private String hallFieldId;

    /**
     * 影厅名称
     */
    private String hallName;

    /**
     * 当前场次价格
     */
    private double price;

    /**
     * 当前场次影厅座位分布
     */
    private String seatFile;

    /**
     * 已购买票的座位位置
     */
    private String soldSeats;
}
