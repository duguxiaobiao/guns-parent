package com.stylefeng.guns.api.movie.model.vo;

import com.stylefeng.guns.api.base.BaseModel;
import lombok.Data;

/**
 * @author ztkj-hzb
 * @Date 2019/8/30 10:47
 * @Description 横幅广告实体
 */
@Data
public class BannerVo extends BaseModel {

    /**
     * 主键id
     */
    private String bannerId;
    /**
     * 广告地址
     */
    private String bannerAddress;
    /**
     * 播放路径
     */
    private String bannerUrl;

}
