package com.stylefeng.guns.api.movie;

import com.stylefeng.guns.api.movie.model.vo.ActorVo;
import com.stylefeng.guns.api.movie.model.vo.FilmDescVo;
import com.stylefeng.guns.api.movie.model.vo.ImgVo;

import java.util.List;

/**
 * @author ztkj-hzb
 * @Date 2019/9/16 14:20
 * @Description 电影相关异步调用服务api
 */
public interface MovieAsyncServiceAPI {


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
