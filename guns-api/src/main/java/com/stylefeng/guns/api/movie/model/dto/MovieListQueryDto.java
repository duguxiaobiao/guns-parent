package com.stylefeng.guns.api.movie.model.dto;

import com.stylefeng.guns.api.base.BaseModel;
import lombok.Data;

/**
 * @author ztkj-hzb
 * @Date 2019/9/11 14:43
 * @Description 查询电影列表处理实体
 */
@Data
public class MovieListQueryDto extends BaseModel {


    /**
     * 查询类型 1-正在热映，2-即将上映，3-经典影片
     */
    private Integer showType;
    /**
     * 排序方式 1-按热门搜索，2-按时间搜索，3-按评价搜索
     */
    private Integer sortId;
    /**
     * 类型编号
     */
    private Integer catId;
    /**
     * 区域编号
     */
    private Integer sourceId;
    /**
     * 年代编号
     */
    private Integer yearId;
    /**
     * 影片列表当前页
     */
    private Integer nowPage;
    /**
     * 每页显示条数
     */
    private Integer pageSize;


}
