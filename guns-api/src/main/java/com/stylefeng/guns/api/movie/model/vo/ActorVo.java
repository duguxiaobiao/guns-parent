package com.stylefeng.guns.api.movie.model.vo;

import com.stylefeng.guns.api.base.BaseModel;
import lombok.Data;

/**
 * @author ztkj-hzb
 * @Date 2019/9/12 14:14
 * @Description 演员实体
 */
@Data
public class ActorVo extends BaseModel {

    /**
     * 图片地址
     */
    private String imgAddress;
    /**
     * 演员名称
     */
    private String directorName;
    /**
     * 角色名称
     */
    private String roleName;

}
