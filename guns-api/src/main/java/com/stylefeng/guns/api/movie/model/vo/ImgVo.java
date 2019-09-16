package com.stylefeng.guns.api.movie.model.vo;

import com.stylefeng.guns.api.base.BaseModel;
import lombok.Data;

/**
 * @author ztkj-hzb
 * @Date 2019/9/12 14:16
 * @Description 图片实体
 */
@Data
public class ImgVo extends BaseModel {


    /**
     * 主图片
     */
    private String mainImg;
    /**
     * 其他图片1
     */
    private String img01;
    /**
     * 其他图片2
     */
    private String img02;
    /**
     * 其他图片3
     */
    private String img03;
    /**
     * 其他图片4
     */
    private String img04;

}
