package com.stylefeng.guns.gateway.modular.movie.vo;

import com.stylefeng.guns.api.base.BaseModel;
import com.stylefeng.guns.api.movie.model.vo.BannerVo;
import com.stylefeng.guns.api.movie.model.vo.FilmInfoVo;
import com.stylefeng.guns.api.movie.model.vo.FilmVo;
import lombok.Data;

import java.util.List;

/**
 * @author ztkj-hzb
 * @Date 2019/8/30 10:46
 * @Description 影片响应实体
 */
@Data
public class MovieIndexVo extends BaseModel {

    /**
     * 广告集合
     */
    private List<BannerVo> banners;

    /**
     * 热门电影实体
     */
    private FilmVo hotFilms;

    /**
     * 待上映电影实体
     */
    private FilmVo soonFilms;

    /**
     * 排行榜
     */
    private List<FilmInfoVo> boxRanking;

    /**
     * 期待榜
     */
    private List<FilmInfoVo> expectRanking;

    /**
     * top前10
     */
    private List<FilmInfoVo> top;
}
