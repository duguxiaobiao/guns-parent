package com.stylefeng.guns.api.cinema.model.vo;

import com.stylefeng.guns.api.base.BaseModel;
import lombok.Data;

/**
 * @author ztkj-hzb
 * @Date 2019/9/17 10:01
 * @Description 影院详细信息实体
 */
@Data
public class CinemaInfoVo extends BaseModel {

    /**
     * id
     */
    private Integer cinemaId;

    /**
     * 影院图片url路径
     */
    private String imgUrl;

    /**
     * 影院名称
     */
    private String cinemaName;

    /**
     * 影院地址
     */
    private String cinemaAddress;

    /**
     * 影院电话
     */
    private String cinemaPhone;
}
