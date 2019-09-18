package com.stylefeng.guns.api.cinema.model.vo;

import com.stylefeng.guns.api.base.BaseModel;
import lombok.Data;

/**
 * @author ztkj-hzb
 * @Date 2019/9/16 16:17
 * @Description 影院信息响应实体
 */
@Data
public class CinemaVo extends BaseModel {

    /**
     * 主键id
     */
    private Integer uuid;

    /**
     * 影院名称
     */
    private String cinemaName;

    /**
     * 影院地址
     */
    private String address;

    /**
     * 最低票价
     */
    private double minimumPrice;

}
