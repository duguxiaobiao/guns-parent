package com.stylefeng.guns.gateway.modular.movie.vo;

import com.stylefeng.guns.api.base.BaseModel;
import com.stylefeng.guns.api.movie.model.vo.CatInfoVo;
import com.stylefeng.guns.api.movie.model.vo.SourceInfoVo;
import com.stylefeng.guns.api.movie.model.vo.YearInfoVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author ztkj-hzb
 * @Date 2019/9/11 13:50
 * @Description 影片条件实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieConditionVo extends BaseModel {

    /**
     * 类别集合
     */
    private List<CatInfoVo> catInfo;
    /**
     * 来源集合
     */
    private List<SourceInfoVo> sourceInfo;
    /**
     * 年份集合
     */
    private List<YearInfoVo> yearInfo;


}
