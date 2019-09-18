package com.stylefeng.guns.api.cinema.model.dto;

import com.stylefeng.guns.api.base.BaseModel;
import lombok.Data;

/**
 * @author ztkj-hzb
 * @Date 2019/9/16 16:06
 * @Description 影院
 */
@Data
public class CinemaQueryListDto extends BaseModel {

    /**
     * 影院编号，默认99
     */
    private Integer brandId;

    /**
     * 影厅类型，默认99
     */
    private Integer hallType;

    /**
     * 行政区编号，默认99
     */
    private Integer districtId;

    /**
     * 每页条数,默认12条
     */
    private Integer pageSize;

    /**
     * 当前页数,默认第一页
     */
    private Integer nowPage;
}
