package com.stylefeng.guns.cinema.common.persistence.dao;

import com.stylefeng.guns.api.cinema.model.vo.FilmInfoVo;
import com.stylefeng.guns.cinema.common.persistence.model.MoocHallFilmInfoT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 影厅电影信息表 Mapper 接口
 * </p>
 *
 * @author lonely
 * @since 2019-09-16
 */
public interface MoocHallFilmInfoTMapper extends BaseMapper<MoocHallFilmInfoT> {

    /**
     * 查询指定影院下的所有电影场次信息
     * @param cinemaId
     * @return
     */
    List<FilmInfoVo> getFilmInfoVosByCinemaId(@Param("cinemaId") Integer cinemaId);

    /**
     * 根据放映场次id获取对应的电影信息
     * @param fieldId
     * @return
     */
    FilmInfoVo getFilmInfoVoByFieldId(@Param("fieldId") Integer fieldId);
}
