package com.stylefeng.guns.movie.service;

import com.stylefeng.guns.api.movie.MovieAsyncServiceAPI;
import com.stylefeng.guns.api.movie.model.vo.ActorVo;
import com.stylefeng.guns.api.movie.model.vo.FilmDescVo;
import com.stylefeng.guns.api.movie.model.vo.ImgVo;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author ztkj-hzb
 * @Date 2019/8/30 11:46
 * @Description 默认影片服务实现
 */
@Service
public class DefaultMovieAsyncServiceImpl implements MovieAsyncServiceAPI {

    @Autowired
    private DefaultMovieServiceImpl defaultMovieService;


    /**
     * 获取影片描述信息
     *
     * @param uuid
     * @return
     */
    @Override
    public FilmDescVo getFilmDesc(String uuid) {
        return this.defaultMovieService.getFilmDesc(uuid);
    }

    /**
     * 获取影片图片信息
     *
     * @param uuid
     * @return
     */
    @Override
    public ImgVo getImgs(String uuid) {
        return this.defaultMovieService.getImgs(uuid);
    }

    /**
     * 获取导演信息
     *
     * @param uuid
     * @return
     */
    @Override
    public ActorVo getDectInfo(String uuid) {
        return this.defaultMovieService.getDectInfo(uuid);
    }

    /**
     * 获取演员信息
     *
     * @param uuid
     * @return
     */
    @Override
    public List<ActorVo> getActors(String uuid) {
        return this.defaultMovieService.getActors(uuid);
    }

}
