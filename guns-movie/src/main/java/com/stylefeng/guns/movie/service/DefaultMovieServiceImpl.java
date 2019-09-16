package com.stylefeng.guns.movie.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.movie.MovieServiceAPI;
import com.stylefeng.guns.api.movie.model.dto.MovieListQueryDto;
import com.stylefeng.guns.api.movie.model.vo.*;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.movie.common.persistence.dao.*;
import com.stylefeng.guns.movie.common.persistence.model.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ztkj-hzb
 * @Date 2019/8/30 11:46
 * @Description 默认影片服务实现
 */
@Service
public class DefaultMovieServiceImpl implements MovieServiceAPI {

    @Autowired
    private MoocBannerTMapper moocBannerTMapper;

    @Autowired
    private MoocFilmTMapper moocFilmTMapper;

    @Autowired
    private MoocCatDictTMapper moocCatDictTMapper;

    @Autowired
    private MoocSourceDictTMapper moocSourceDictTMapper;

    @Autowired
    private MoocYearDictTMapper moocYearDictTMapper;

    @Autowired
    private MoocFilmInfoTMapper moocFilmInfoTMapper;

    @Autowired
    private MoocActorTMapper moocActorTMapper;

    /**
     * 获取广告集合
     *
     * @return
     */
    @Override
    public List<BannerVo> getBanners() {
        //查询所有广告
        List<MoocBannerT> moocBannerTS = this.moocBannerTMapper.selectList(null);

        //转化响应实体
        List<BannerVo> bannerVos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(moocBannerTS)) {
            for (MoocBannerT moocBannerT : moocBannerTS) {
                BannerVo bannerVo = new BannerVo();
                bannerVo.setBannerId("" + moocBannerT.getUuid());
                bannerVo.setBannerAddress(moocBannerT.getBannerAddress());
                bannerVo.setBannerUrl(moocBannerT.getBannerUrl());
                bannerVos.add(bannerVo);
            }
        }
        return bannerVos;
    }

    /**
     * 获取热门电影
     *
     * @param isLimit
     * @param nums
     * @return
     */
    @Override
    public FilmVo getHotFilms(boolean isLimit, int nums) {

        FilmVo filmVo = new FilmVo();
        List<FilmInfoVo> filmInfoVos = new ArrayList<>();
        //判断是否是首页热门，还是热门列表
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", 1);
        if (isLimit) {
            //首页的，则只看第一页的
            Page<MoocFilmT> page = new Page<>(1, nums);
            List<MoocFilmT> moocFilmTS = this.moocFilmTMapper.selectPage(page, entityWrapper);
            filmInfoVos = this.do2FilmInfoVos(moocFilmTS);
        } else {
            //
        }
        filmVo.setFilmInfo(filmInfoVos);
        filmVo.setFilmNum(filmInfoVos.size());
        return filmVo;
    }


    /**
     * 将电影信息转换成响应实体
     *
     * @param moocFilmTS
     * @return
     */
    private List<FilmInfoVo> do2FilmInfoVos(List<MoocFilmT> moocFilmTS) {
        List<FilmInfoVo> filmInfoVos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(moocFilmTS)) {
            for (MoocFilmT moocFilmT : moocFilmTS) {
                FilmInfoVo filmInfoVo = new FilmInfoVo();
                filmInfoVo.setFilmId("" + moocFilmT.getUuid());
                filmInfoVo.setFilmType(moocFilmT.getFilmType());
                filmInfoVo.setImgAddress(moocFilmT.getImgAddress());
                filmInfoVo.setFilmName(moocFilmT.getFilmName());
                filmInfoVo.setFilmScore(moocFilmT.getFilmScore());
                filmInfoVo.setExpectNum(moocFilmT.getFilmPresalenum());
                filmInfoVo.setShowTime(DateUtil.getDay(moocFilmT.getFilmTime()));
                filmInfoVo.setBoxNum(moocFilmT.getFilmBoxOffice());
                filmInfoVo.setScore(moocFilmT.getFilmScore());

                filmInfoVos.add(filmInfoVo);
            }
        }
        return filmInfoVos;
    }

    /**
     * 获取待上映电影
     *
     * @param isLimit
     * @param nums
     * @return
     */
    @Override
    public FilmVo getSoonFilms(boolean isLimit, int nums) {
        FilmVo filmVo = new FilmVo();
        List<FilmInfoVo> filmInfoVos = new ArrayList<>();
        //判断是否是首页热门，还是热门列表
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", 2);
        if (isLimit) {
            //首页的，则只看第一页的
            Page<MoocFilmT> page = new Page<>(1, nums);
            List<MoocFilmT> moocFilmTS = this.moocFilmTMapper.selectPage(page, entityWrapper);
            filmInfoVos = this.do2FilmInfoVos(moocFilmTS);
        } else {
            //
        }
        filmVo.setFilmInfo(filmInfoVos);
        filmVo.setFilmNum(filmInfoVos.size());
        return filmVo;
    }

    /**
     * 获取票房排行榜，获取票房排名top10
     *
     * @return
     */
    @Override
    public List<FilmInfoVo> getBoxRanking() {
        //设置参数
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", 1);
        //设置分页参数
        Page<MoocFilmT> page = new Page<>(1, 10, "film_box_office");
        //查询、转换、响应
        List<MoocFilmT> moocFilmTS = this.moocFilmTMapper.selectPage(page, entityWrapper);
        return this.do2FilmInfoVos(moocFilmTS);
    }

    /**
     * 获取人气排行榜，获取人气排行top10
     *
     * @return
     */
    @Override
    public List<FilmInfoVo> getExpectRanking() {
        //设置参数
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", 2);
        //设置分页参数
        Page<MoocFilmT> page = new Page<>(1, 10, "film_preSaleNum");
        //查询、转换、响应
        List<MoocFilmT> moocFilmTS = this.moocFilmTMapper.selectPage(page, entityWrapper);
        return this.do2FilmInfoVos(moocFilmTS);
    }

    /**
     * 获取top10
     *
     * @return
     */
    @Override
    public List<FilmInfoVo> getTop() {
        //设置参数
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", 1);
        //设置分页参数
        Page<MoocFilmT> page = new Page<>(1, 10, "film_score");
        //查询、转换、响应
        List<MoocFilmT> moocFilmTS = this.moocFilmTMapper.selectPage(page, entityWrapper);
        return this.do2FilmInfoVos(moocFilmTS);
    }

    /**
     * 获取影片类型集合
     *
     * @return
     */
    @Override
    public List<CatInfoVo> getCatInfoVos() {
        List<MoocCatDictT> moocCatDictTS = this.moocCatDictTMapper.selectList(null);
        return this.do2CatInfoVos(moocCatDictTS);
    }

    /**
     * 获取影片来源集合
     *
     * @return
     */
    @Override
    public List<SourceInfoVo> getSourceInfoVos() {
        List<MoocSourceDictT> moocSourceDictTS = this.moocSourceDictTMapper.selectList(null);
        return this.do2SourceInfoVos(moocSourceDictTS);
    }

    /**
     * 获取影片年代集合
     *
     * @return
     */
    @Override
    public List<YearInfoVo> getYearInfoVos() {
        List<MoocYearDictT> moocYearDictTS = this.moocYearDictTMapper.selectList(null);
        return this.do2YearInfoVos(moocYearDictTS);
    }

    /**
     * 查询指定条件下的电影列表信息集合
     *
     * @param movieListQueryDto
     * @return
     */
    @Override
    public FilmListVo getMovies(MovieListQueryDto movieListQueryDto) {
        //构建查询条件
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        //影片来源
        if (movieListQueryDto.getSourceId() != 99) {
            entityWrapper.eq("film_score", movieListQueryDto.getSourceId());
        }
        //影片年代
        if (movieListQueryDto.getYearId() != 99) {
            entityWrapper.eq("film_date", movieListQueryDto.getYearId());
        }
        //影片类别
        if (movieListQueryDto.getCatId() != 99) {
            //因为每个影片可能存在多个类别，所以保存的格式是 #1#2#22# ,所以这里使用模糊查询
            entityWrapper.like("film_cats", MessageFormat.format("%#{0}#%", movieListQueryDto.getCatId()));
        }
        //影片类型
        entityWrapper.eq("film_status", movieListQueryDto.getShowType());

        //排序方式
        String sortName = null;
        if (movieListQueryDto.getSortId() == 1) {
            //按热门搜索
            sortName = "film_box_office";
        } else if (movieListQueryDto.getSortId() == 2) {
            //按时间排序
            sortName = "film_time";
        } else if (movieListQueryDto.getSortId() == 3) {
            //按评价排序
            sortName = "film_score";
        }
        Page<MoocFilmT> page = new Page<>(movieListQueryDto.getNowPage(), movieListQueryDto.getPageSize(), sortName);
        List<MoocFilmT> moocFilmTS = this.moocFilmTMapper.selectPage(page, entityWrapper);

        FilmListVo filmListVo = new FilmListVo();
        filmListVo.setFilmVos(this.do2FilmInfoVos(moocFilmTS));
        filmListVo.setNowPage(movieListQueryDto.getNowPage());
        filmListVo.setTotalPage((int) page.getTotal());
        return filmListVo;
    }

    /**
     * 获取指定条件下的电影明细信息
     *
     * @param searchParam
     * @param searchType
     * @return
     */
    @Override
    public FilmDetailVo getFilmDetail(String searchParam, int searchType) {

        //获取指定条件下的电影信息
        FilmDetailVo filmDetailVo;
        if (searchType == 0) {
            //根据电影编号查询
            filmDetailVo = this.moocFilmTMapper.getFilmDetailByUuid(searchParam);
        } else {
            //根据电影名称查询
            filmDetailVo = this.moocFilmTMapper.getFilmDetailByName(searchParam);
        }
        return filmDetailVo;
    }

    /**
     * 获取影片描述信息
     *
     * @param uuid
     * @return
     */
    @Override
    public FilmDescVo getFilmDesc(String uuid) {
        MoocFilmInfoT moocFilmInfoT = this.getMoocFilmInfoT(uuid);
        FilmDescVo filmDescVo = new FilmDescVo();
        filmDescVo.setBiography(moocFilmInfoT.getBiography());
        return filmDescVo;
    }

    /**
     * 获取影片图片信息
     *
     * @param uuid
     * @return
     */
    @Override
    public ImgVo getImgs(String uuid) {
        MoocFilmInfoT moocFilmInfoT = this.getMoocFilmInfoT(uuid);

        String filmImgs = moocFilmInfoT.getFilmImgs();
        ImgVo imgVo = new ImgVo();
        if (StringUtils.isNotBlank(filmImgs)) {
            String[] split = filmImgs.split(",");
            //TODO 这里当保存的数据不按照正常的来这样写就会出问题，目前只是实现功能，暂时忽略
            imgVo.setMainImg(split[0]);
            imgVo.setImg01(split[1]);
            imgVo.setImg02(split[2]);
            imgVo.setImg03(split[3]);
            imgVo.setImg04(split[4]);
        }

        return imgVo;
    }

    /**
     * 获取导演信息
     *
     * @param uuid
     * @return
     */
    @Override
    public ActorVo getDectInfo(String uuid) {
        MoocFilmInfoT moocFilmInfoT = this.getMoocFilmInfoT(uuid);

        //根据演员id查询对应的导演信息
        MoocActorT moocActorT = this.moocActorTMapper.selectById(moocFilmInfoT.getDirectorId());

        //转换成响应实体
        ActorVo actorVo = new ActorVo();
        actorVo.setImgAddress(moocActorT.getActorImg());
        actorVo.setDirectorName(moocActorT.getActorName());
        return actorVo;
    }

    /**
     * 获取演员信息
     *
     * @param uuid
     * @return
     */
    @Override
    public List<ActorVo> getActors(String uuid) {
        return this.moocActorTMapper.getActorVos(uuid);
    }

    /**
     * 根据影片id获取对应的影片明细信息
     *
     * @param filmId
     * @return
     */
    private MoocFilmInfoT getMoocFilmInfoT(String filmId) {
        MoocFilmInfoT selectFilmInfo = new MoocFilmInfoT();
        selectFilmInfo.setFilmId(filmId);
        return this.moocFilmInfoTMapper.selectOne(selectFilmInfo);
    }


    /**
     * 抓换影片年代实体
     *
     * @param moocYearDictTS
     * @return
     */
    private List<YearInfoVo> do2YearInfoVos(List<MoocYearDictT> moocYearDictTS) {
        List<YearInfoVo> yearInfoVos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(moocYearDictTS)) {
            for (MoocYearDictT moocYearDictT : moocYearDictTS) {
                YearInfoVo yearInfoVo = new YearInfoVo();
                yearInfoVo.setYearId("" + moocYearDictT.getUuid());
                yearInfoVo.setYearName(moocYearDictT.getShowName());
                yearInfoVos.add(yearInfoVo);
            }
        }
        return yearInfoVos;
    }

    /**
     * 转换影片来源实体
     *
     * @param moocSourceDictTS
     * @return
     */
    private List<SourceInfoVo> do2SourceInfoVos(List<MoocSourceDictT> moocSourceDictTS) {
        List<SourceInfoVo> sourceInfoVos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(moocSourceDictTS)) {
            for (MoocSourceDictT moocSourceDictT : moocSourceDictTS) {
                SourceInfoVo sourceInfoVo = new SourceInfoVo();
                sourceInfoVo.setSourceId("" + moocSourceDictT.getUuid());
                sourceInfoVo.setSourceName(moocSourceDictT.getShowName());
                sourceInfoVos.add(sourceInfoVo);
            }
        }
        return sourceInfoVos;
    }


    /**
     * 将影院类别集合转换成响应实体
     *
     * @param moocCatDictTS
     * @return
     */
    private List<CatInfoVo> do2CatInfoVos(List<MoocCatDictT> moocCatDictTS) {
        List<CatInfoVo> catInfoVos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(moocCatDictTS)) {
            for (MoocCatDictT moocCatDictT : moocCatDictTS) {
                CatInfoVo catInfoVo = new CatInfoVo();
                catInfoVo.setCatId("" + moocCatDictT.getUuid());
                catInfoVo.setCatName(moocCatDictT.getShowName());
                //添加到集合中
                catInfoVos.add(catInfoVo);
            }
        }
        return catInfoVos;
    }
}
