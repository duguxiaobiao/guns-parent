package com.stylefeng.guns.api.cinema.model.vo;

import com.stylefeng.guns.api.base.BaseModel;
import lombok.Data;

/**
 * @author ztkj-hzb
 * @Date 2019/9/16 17:33
 * @Description 影院影厅音效响应实体
 */
@Data
public class HallTypeVo extends BaseModel {

    /**
     * id
     */
    private Integer halltypeId;

    /**
     * 音效名称
     */
    private String halltypeName;

    /**
     * 是否选中
     */
    private boolean isActive;
}
