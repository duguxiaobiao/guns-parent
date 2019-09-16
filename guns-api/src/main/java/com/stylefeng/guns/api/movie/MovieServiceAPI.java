package com.stylefeng.guns.api.movie;

import com.stylefeng.guns.api.movie.model.dto.MovieListQueryDto;
import com.stylefeng.guns.api.movie.model.vo.*;

import java.util.List;

/**
 * @author ztkj-hzb
 * @Date 2019/8/30 11:25
 * @Description 影片服务api
 */
public interface MovieServiceAPI {

    /**
     * 获取广告集合
     *
     * @return
     */
    List<BannerVo> getBanners();

    /**
     * 获取热门电影
     *
     * @param isLimit
     * @param nums
     * @return
     */
    FilmVo getHotFilms(boolean isLimit, int nums);

    /**
     * 获取待上映电影
     *
     * @param isLimit
     * @param nums
     * @return
     */
    FilmVo getSoonFilms(boolean isLimit, int nums);

    /**
     * 获取票房排行榜
     *
     * @return
     */
    List<FilmInfoVo> getBoxRanking();

    /**
     * 获取人气排行榜
     *
     * @return
     */
    List<FilmInfoVo> getExpectRanking();

    /**
     * 获取top100
     *
     * @return
     */
    List<FilmInfoVo> getTop();


    /**
     * 获取影片类型集合
     *
     * @return
     */
    List<CatInfoVo> getCatInfoVos();

    /**
     * 获取影片来源集合
     *
     * @return
     */
    List<SourceInfoVo> getSourceInfoVos();

    /**
     * 获取影片年代集合
     *
     * @return
     */
    List<YearInfoVo> getYearInfoVos();


    /**
     * 查询指定条件下的电影列表信息集合
     *
     * @param movieListQueryDto
     * @return
     */
    FilmListVo getMovies(MovieListQueryDto movieListQueryDto);

    /**
     * 获取指定条件下的电影明细信息
     *
     * @param searchParam
     * @param searchType
     * @return
     */
    FilmDetailVo getFilmDetail(String searchParam, int searchType);

    /**
     * 获取影片描述信息
     *
     * @param uuid
     * @return
     */
    FilmDescVo getFilmDesc(String uuid);

    /**
     * 获取影片图片信息
     *
     * @param uuid
     * @return
     */
    ImgVo getImgs(String uuid);

    /**
     * 获取导演信息
     *
     * @param uuid
     * @return
     */
    ActorVo getDectInfo(String uuid);

    /**
     * 获取演员信息
     *
     * @param uuid
     * @return
     */
    List<ActorVo> getActors(String uuid);

}
