package com.stylefeng.guns.gateway.modular.movie.vo;

import com.stylefeng.guns.api.base.BaseModel;
import com.stylefeng.guns.api.movie.model.vo.FilmInfoVo;
import lombok.Data;

import java.util.List;

/**
 * @author ztkj-hzb
 * @Date 2019/9/11 14:34
 * @Description 电影列表响应实体
 */
@Data
public class MovieListVo extends BaseModel {


    /**
     * 电影列表
     */
    private List<FilmInfoVo> filmInfoVos;


}
