package com.stylefeng.guns.movie.common.persistence.dao;

import com.stylefeng.guns.api.movie.model.vo.ActorVo;
import com.stylefeng.guns.movie.common.persistence.model.MoocActorT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 演员表 Mapper 接口
 * </p>
 *
 * @author lonely
 * @since 2019-08-30
 */
public interface MoocActorTMapper extends BaseMapper<MoocActorT> {

    /**
     * 根据影片id获取对应的演员信息
     * @param filmId
     * @return
     */
    List<ActorVo> getActorVos(@Param("filmId") String filmId);

}
