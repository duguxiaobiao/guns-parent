package com.stylefeng.guns.api.movie.model.vo;

import com.stylefeng.guns.api.base.BaseModel;
import lombok.Data;

/**
 * @author ztkj-hzb
 * @Date 2019/9/11 16:16
 * @Description
 */
@Data
public class FilmDetailVo extends BaseModel {

    /**
     * 电影主键id
     */
    private String filmId;

    /**
     * 电影名
     */
    private String filmName;
    /**
     * 英文名
     */
    private String filmEnName;
    /**
     * 图片地址
     */
    private String imgAddress;
    /**
     * 评分
     */
    private String score;
    /**
     * 评价人数
     */
    private String socreNum;
    /**
     * 总数
     */
    private String totalBox;
    /**
     * 资料01
     */
    private String info01;
    /**
     * 资料02
     */
    private String info02;
    /**
     * 资料03
     */
    private String info03;


    /**
     * 影片的描述信息
     */
    private FilmDescVo info04;

    /**
     * 图片资源
     */
    private ImgVo imgs;


}
