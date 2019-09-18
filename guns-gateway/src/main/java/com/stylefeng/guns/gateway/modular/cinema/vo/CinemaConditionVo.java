package com.stylefeng.guns.gateway.modular.cinema.vo;

import com.stylefeng.guns.api.base.BaseModel;
import com.stylefeng.guns.api.cinema.model.vo.AreaVo;
import com.stylefeng.guns.api.cinema.model.vo.BrandVo;
import com.stylefeng.guns.api.cinema.model.vo.HallTypeVo;
import lombok.Data;

import java.util.List;

/**
 * @author ztkj-hzb
 * @Date 2019/9/16 17:51
 * @Description 影院条件响应实体
 */
@Data
public class CinemaConditionVo extends BaseModel {

    /**
     * 品牌集合
     */
    private List<BrandVo> brandList;

    /**
     * 区域集合
     */
    private List<AreaVo> areaList;

    /**
     * 影厅音效集合
     */
    private List<HallTypeVo> halltypeList;

}
