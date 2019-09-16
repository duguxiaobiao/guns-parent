package com.stylefeng.guns.api.movie.model.vo;

import com.stylefeng.guns.api.base.BaseModel;
import lombok.Data;

import java.util.List;

/**
 * @author ztkj-hzb
 * @Date 2019/9/12 14:18
 * @Description 影片描述实体
 */
@Data
public class FilmDescVo extends BaseModel {

    /**
     * 描述信息
     */
    private String biography;

    /**
     * 导演信息
     */
    private ActorVo director;

    /**
     * 演员信息
     */
    private List<ActorVo> actors;


}
