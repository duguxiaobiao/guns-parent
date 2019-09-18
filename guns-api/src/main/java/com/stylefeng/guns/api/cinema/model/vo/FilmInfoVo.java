package com.stylefeng.guns.api.cinema.model.vo;

import com.stylefeng.guns.api.base.BaseModel;
import lombok.Data;

import java.util.List;

/**
 * @author ztkj-hzb
 * @Date 2019/9/17 10:29
 * @Description 放映电影响应实体
 */
@Data
public class FilmInfoVo extends BaseModel {

    /**
     * 电影id
     */
    private String filmId;

    /**
     * 电影名称
     */
    private String filmName;

    /**
     * 电影时长
     */
    private String filmLength;

    /**
     * 电影语言
     */
    private String filmType;

    /**
     * 电影类型
     */
    private String filmCats;

    /**
     * 演员信息
     */
    private String actors;

    /**
     * 电影图片
     */
    private String imgAddress;

    /**
     * 对应的场次信息
     */
    private List<FieldVo> filmFields;


}
