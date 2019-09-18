package com.stylefeng.guns.api.cinema.model.vo;

import com.stylefeng.guns.api.base.BaseModel;
import lombok.Data;

/**
 * @author ztkj-hzb
 * @Date 2019/9/16 17:31
 * @Description 影院品牌实体
 */
@Data
public class BrandVo extends BaseModel {

    /**
     * 品牌id
     */
    private Integer brandId;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 是否选中
     */
    private boolean isActive;

}
