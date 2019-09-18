package com.stylefeng.guns.gateway.modular.cinema.vo;

import com.stylefeng.guns.api.base.BaseModel;
import com.stylefeng.guns.api.cinema.model.vo.CinemaInfoVo;
import com.stylefeng.guns.api.cinema.model.vo.FilmInfoVo;
import lombok.Data;

import java.util.List;

/**
 * @author ztkj-hzb
 * @Date 2019/9/17 11:33
 * @Description 播放场次列表响应实体
 */
@Data
public class CinemaFieldListVo extends BaseModel {

    /**
     * 影院信息
     */
    private CinemaInfoVo cinemaInfoVo;

    /**
     * 该影院下的所有电影场次信息
     */
    private List<FilmInfoVo> filmList;
}
