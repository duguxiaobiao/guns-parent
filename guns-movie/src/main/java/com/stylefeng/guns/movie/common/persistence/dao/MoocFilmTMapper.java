package com.stylefeng.guns.movie.common.persistence.dao;

import com.stylefeng.guns.api.movie.model.vo.FilmDetailVo;
import com.stylefeng.guns.movie.common.persistence.model.MoocFilmT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 影片主表 Mapper 接口
 * </p>
 *
 * @author lonely
 * @since 2019-08-30
 */
public interface MoocFilmTMapper extends BaseMapper<MoocFilmT> {

    /**
     * 查询指定电影名对应的电影明细
     * @param filmName
     * @return
     */
    FilmDetailVo getFilmDetailByName(@Param("filmName") String filmName);

    /**
     * 查询指定uuid对应的电影明细
     * @param uuid
     * @return
     */
    FilmDetailVo getFilmDetailByUuid(@Param("uuid") String uuid);


}
