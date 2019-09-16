package com.stylefeng.guns.api.movie.model.vo;

import com.stylefeng.guns.api.base.BaseModel;
import lombok.Data;

/**
 * @author ztkj-hzb
 * @Date 2019/8/30 15:06
 * @Description 电影类别响应实体
 */
@Data
public class CatInfoVo extends BaseModel {

    /**
     * 类别id
     */
    private String catId;

    /**
     * 类别名称
     */
    private String catName;

    /**
     * 是否有效
     */
    private boolean isAvtive;

}
