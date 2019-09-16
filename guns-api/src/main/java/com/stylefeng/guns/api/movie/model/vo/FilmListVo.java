package com.stylefeng.guns.api.movie.model.vo;

import com.stylefeng.guns.api.base.BaseModel;
import lombok.Data;

import java.util.List;

/**
 * @author ztkj-hzb
 * @Date 2019/9/11 15:01
 * @Description 电影列表实体
 */
@Data
public class FilmListVo extends BaseModel {

    /**
     * 当前是第几页
     */
    private Integer nowPage;
    /**
     * 一共有多少页
     */
    private Integer totalPage;

    /**
     * 当前页的电影列表集合
     */
    private List<FilmInfoVo> filmVos;

}
