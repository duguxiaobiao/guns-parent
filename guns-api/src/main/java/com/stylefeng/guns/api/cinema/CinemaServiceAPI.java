package com.stylefeng.guns.api.cinema;

import com.stylefeng.guns.api.cinema.model.dto.CinemaQueryListDto;
import com.stylefeng.guns.api.cinema.model.vo.*;

import java.util.List;

/**
 * @author ztkj-hzb
 * @Date 2019/9/16 16:16
 * @Description 影院服务接口api
 */
public interface CinemaServiceAPI {

    /**
     * 获取指定条件下的影院列表
     *
     * @param cinemaQueryListDto
     * @return
     */
    CinemaPageVo getCinemas(CinemaQueryListDto cinemaQueryListDto);

    /**
     * 获取所有影院品牌
     *
     * @return
     */
    List<BrandVo> getBrandVos();

    /**
     * 获取所有影院区域地址
     *
     * @return
     */
    List<AreaVo> getAreaVos();

    /**
     * 获取所有影厅音效实体
     *
     * @return
     */
    List<HallTypeVo> getHallTypeVos();

    /**
     * 根据影院id获取对应的影院信息
     *
     * @param cinemaId
     * @return
     */
    CinemaInfoVo getCinemaInfoById(Integer cinemaId);

    /**
     * 根据影院id获取对应的电影和场次信息
     *
     * @param cinemaId
     * @return
     */
    List<FilmInfoVo> getFilmInfos(Integer cinemaId);

    /**
     * 根据放映场次id获取对应的电影信息
     *
     * @param fieldId
     * @return
     */
    FilmInfoVo getFilmInfoVo(Integer fieldId);

    /**
     * 根据放映场次id获取对应的影厅信息
     *
     * @param fieldId
     * @return
     */
    HallInfoVo getHallInfo(Integer fieldId);
}
