package com.stylefeng.guns.gateway.modular.cinema.vo;

import com.stylefeng.guns.api.base.BaseModel;
import com.stylefeng.guns.api.cinema.model.vo.CinemaInfoVo;
import com.stylefeng.guns.api.cinema.model.vo.FilmInfoVo;
import com.stylefeng.guns.api.cinema.model.vo.HallInfoVo;
import lombok.Data;

/**
 * @author ztkj-hzb
 * @Date 2019/9/17 11:40
 * @Description 指定场次的详细信息
 */
@Data
public class CinemaFieldInfoVo extends BaseModel {

    /**
     * 电影信息
     */
    private FilmInfoVo filmInfoVo;

    /**
     * 影院信息
     */
    private CinemaInfoVo cinemaInfoVo;

    /**
     * 影厅信息
     */
    private HallInfoVo hallInfoVo;
}
