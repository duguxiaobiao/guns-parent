package com.stylefeng.guns.gateway.modular.cinema.qo;

import com.stylefeng.guns.api.base.BaseModel;
import lombok.Data;

/**
 * @author ztkj-hzb
 * @Date 2019/9/16 15:36
 * @Description 查询影院列表请求实体
 */
@Data
public class CinemaListQo extends BaseModel {


    /**
     * 影院编号，默认99
     */
    private Integer brandId = 99;

    /**
     * 影厅类型，默认99
     */
    private Integer hallType = 99;

    /**
     * 行政区编号，默认99
     */
    private Integer districtId = 99;

    /**
     * 每页条数,默认12条
     */
    private Integer pageSize = 12;

    /**
     * 当前页数,默认第一页
     */
    private Integer nowPage = 1;


}
