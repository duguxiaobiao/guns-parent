package com.stylefeng.guns.api.cinema.model.vo;

import com.stylefeng.guns.api.base.BaseModel;
import lombok.Data;

import java.util.List;

/**
 * @author ztkj-hzb
 * @Date 2019/9/16 16:40
 * @Description 影院列表分页查询响应实体
 */
@Data
public class CinemaPageVo extends BaseModel {

    /**
     * 当前第几页
     */
    private Integer nowPage;

    /**
     * 总条数
     */
    private Integer totalPage;

    /**
     * 当前页的影院信息
     */
    private List<CinemaVo> cinemas;


}
