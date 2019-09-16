package com.stylefeng.guns.api.movie.model.vo;

import com.stylefeng.guns.api.base.BaseModel;
import lombok.Data;

import java.util.List;

/**
 * @author ztkj-hzb
 * @Date 2019/8/30 10:51
 * @Description 电影首页响应实体
 */
@Data
public class FilmVo extends BaseModel {


    private int filmNum;

    /**
     * 电影集合
     */
    private List<FilmInfoVo> filmInfo;

}
