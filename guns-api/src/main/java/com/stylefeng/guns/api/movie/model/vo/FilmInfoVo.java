package com.stylefeng.guns.api.movie.model.vo;

import com.stylefeng.guns.api.base.BaseModel;
import lombok.Data;

/**
 * @author ztkj-hzb
 * @Date 2019/8/30 10:52
 * @Description 影片明细响应实体
 */
@Data
public class FilmInfoVo extends BaseModel {

    /**
     * 影片id
     */
    private String filmId;

    /**
     * 影片类型
     */
    private Integer filmType;

    /**
     * 图片地址
     */
    private String imgAddress;

    /**
     * 影片名称
     */
    private String filmName;

    /**
     * 影片评分
     */
    private String filmScore;

    /**
     * 期待人数
     */
    private int expectNum;

    /**
     * 上映时间
     */
    private String showTime;

    /**
     * 电影排行
     */
    private int boxNum;

    /**
     * 评分
     */
    private String score;
}
