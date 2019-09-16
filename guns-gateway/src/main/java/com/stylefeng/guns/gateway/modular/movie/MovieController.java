package com.stylefeng.guns.gateway.modular.movie;

import com.stylefeng.guns.api.movie.MovieAsyncServiceAPI;
import com.stylefeng.guns.api.movie.MovieServiceAPI;
import com.stylefeng.guns.api.movie.model.dto.MovieListQueryDto;
import com.stylefeng.guns.api.movie.model.vo.*;
import com.stylefeng.guns.gateway.modular.auth.vo.CommonRespose;
import com.stylefeng.guns.gateway.modular.auth.vo.MovieIndexResponse;
import com.stylefeng.guns.gateway.modular.auth.vo.MovieListResponse;
import com.stylefeng.guns.gateway.modular.movie.qo.MovieQueryQo;
import com.stylefeng.guns.gateway.modular.movie.vo.MovieConditionVo;
import com.stylefeng.guns.gateway.modular.movie.vo.MovieIndexVo;
import com.stylefeng.guns.gateway.modular.movie.vo.MovieListVo;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * @author ztkj-hzb
 * @Date 2019/8/30 10:44
 * @Description
 */
@RestController
@RequestMapping("/movie")
public class MovieController {

    @Reference
    private MovieServiceAPI movieServiceAPI;

    /**
     * async 表示该接口中的服务都是异步调用
     */
    @Reference(async = true)
    private MovieAsyncServiceAPI movieAsyncServiceAPI;

    private static final String IMG_PRE = "http://img.meetingshop.cn/";


    /**
     * 获取首页数据
     *
     * @return
     */
    @GetMapping("/getIndex")
    public CommonRespose getIndex() {
        MovieIndexVo movieIndexVo = new MovieIndexVo();
        movieIndexVo.setBanners(this.movieServiceAPI.getBanners());
        movieIndexVo.setHotFilms(this.movieServiceAPI.getHotFilms(true, 8));
        movieIndexVo.setSoonFilms(this.movieServiceAPI.getSoonFilms(true, 8));
        movieIndexVo.setBoxRanking(this.movieServiceAPI.getBoxRanking());
        movieIndexVo.setExpectRanking(this.movieServiceAPI.getExpectRanking());
        movieIndexVo.setTop(this.movieServiceAPI.getTop());

        //响应
        return MovieIndexResponse.success(IMG_PRE, movieIndexVo);
    }


    /**
     * 影片条件列表查询接口
     *
     * @param catId
     * @param sourceId
     * @param yearId
     * @return
     */
    @GetMapping("/getCondition")
    public CommonRespose getConditionList(@RequestParam(name = "catId", defaultValue = "99", required = false) String catId,
                                          @RequestParam(name = "sourceId", defaultValue = "99", required = false) String sourceId,
                                          @RequestParam(name = "yearId", defaultValue = "99", required = false) String yearId) {

        List<CatInfoVo> catInfoVos = this.movieServiceAPI.getCatInfoVos();
        List<CatInfoVo> resultCatInfoVos = catInfoVos.stream().peek(catInfoVo -> {
            if (catInfoVo.getCatId().equals(catId)) {
                catInfoVo.setAvtive(true);
            }
        }).collect(Collectors.toList());


        List<SourceInfoVo> sourceInfoVos = this.movieServiceAPI.getSourceInfoVos();
        List<SourceInfoVo> resultSourceInfos = sourceInfoVos.stream().peek(sourceInfoVo -> {
            if (sourceInfoVo.getSourceId().equals(sourceId)) {
                sourceInfoVo.setActive(true);
            }
        }).collect(Collectors.toList());

        List<YearInfoVo> yearInfoVos = this.movieServiceAPI.getYearInfoVos();
        List<YearInfoVo> resultYearInfos = yearInfoVos.stream().peek(yearInfoVo -> {
            if (yearInfoVo.getYearId().equals(yearId)) {
                yearInfoVo.setActive(true);
            }
        }).collect(Collectors.toList());

        MovieConditionVo movieConditionVo = new MovieConditionVo(resultCatInfoVos, resultSourceInfos, resultYearInfos);
        return CommonRespose.success(movieConditionVo);
    }


    /**
     * 查询指定条件下的电影列表
     *
     * @param movieQueryQo
     * @return
     */
    @GetMapping("/getMovies")
    public CommonRespose getMovies(MovieQueryQo movieQueryQo) {

        //根据条件查询
        MovieListQueryDto movieListQueryDto = new MovieListQueryDto();
        BeanUtils.copyProperties(movieQueryQo, movieListQueryDto);

        //调用接口返回电影集合
        FilmListVo movies = this.movieServiceAPI.getMovies(movieListQueryDto);

        //响应实体
        MovieListVo movieListVo = new MovieListVo();
        movieListVo.setFilmInfoVos(movies.getFilmVos());

        String imgPre = "http://img.meetingshop.cn/";

        return MovieListResponse.success(imgPre, movies.getNowPage(), movies.getTotalPage(), movieListVo);
    }


    /**
     * 影片详情查询接口
     *
     * @param searchParam
     * @param searchType
     * @return
     */
    @GetMapping("/{searchParam}")
    public CommonRespose searchMovie(@PathVariable("searchParam") String searchParam, int searchType) throws ExecutionException, InterruptedException {

        //先获取指定电影的明细
        FilmDetailVo filmDetail = this.movieServiceAPI.getFilmDetail(searchParam, searchType);
        if (filmDetail == null) {
            return CommonRespose.businessFail("查询失败，无影片可加载");
        }

        //获取影片的描述信息
        //FilmDescVo filmDesc = this.movieServiceAPI.getFilmDesc(filmDetail.getFilmId());
        this.movieAsyncServiceAPI.getFilmDesc(filmDetail.getFilmId());
        Future<FilmDescVo> filmDescFuture = RpcContext.getContext().getFuture();


        //获取影片图片信息
        //ImgVo imgs = this.movieServiceAPI.getImgs(filmDetail.getFilmId());
        this.movieAsyncServiceAPI.getImgs(filmDetail.getFilmId());
        Future<ImgVo> imgVoFuture = RpcContext.getContext().getFuture();

        //获取导演信息
        //ActorVo dectInfo = this.movieServiceAPI.getDectInfo(filmDetail.getFilmId());
        this.movieAsyncServiceAPI.getDectInfo(filmDetail.getFilmId());
        Future<ActorVo> actorVoFuture = RpcContext.getContext().getFuture();

        //获取演员信息
        //List<ActorVo> actors = this.movieServiceAPI.getActors(filmDetail.getFilmId());
        this.movieAsyncServiceAPI.getActors(filmDetail.getFilmId());
        Future<List<ActorVo>> actorsFuture = RpcContext.getContext().getFuture();

        //影片描述信息
        FilmDescVo filmDesc = filmDescFuture.get();

        //获取图片信息
        ImgVo imgs = imgVoFuture.get();

        //导演信息
        ActorVo dectInfo = actorVoFuture.get();

        //演员信息
        List<ActorVo> actors = actorsFuture.get();

        filmDesc.setDirector(dectInfo);
        filmDesc.setActors(actors);
        filmDetail.setImgs(imgs);
        filmDetail.setInfo04(filmDesc);
        return MovieIndexResponse.success("http://img.meetingshop.cn/", filmDetail);
    }


}
