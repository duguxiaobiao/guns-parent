package com.stylefeng.guns.cinema.common.persistence.dao;

import com.stylefeng.guns.api.cinema.model.vo.HallInfoVo;
import com.stylefeng.guns.cinema.common.persistence.model.MoocFieldT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 放映场次表 Mapper 接口
 * </p>
 *
 * @author lonely
 * @since 2019-09-16
 */
public interface MoocFieldTMapper extends BaseMapper<MoocFieldT> {

    /**
     * 查询指定场次的影厅信息
     * @param fieldId
     * @return
     */
    HallInfoVo getHallInfoByFieldId(@Param("fieldId") Integer fieldId);
}
