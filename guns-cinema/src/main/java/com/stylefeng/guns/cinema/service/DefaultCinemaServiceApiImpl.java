package com.stylefeng.guns.cinema.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.cinema.CinemaServiceAPI;
import com.stylefeng.guns.api.cinema.model.dto.CinemaQueryListDto;
import com.stylefeng.guns.api.cinema.model.vo.*;
import com.stylefeng.guns.cinema.common.persistence.dao.*;
import com.stylefeng.guns.cinema.common.persistence.model.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ztkj-hzb
 * @Date 2019/9/16 16:21
 * @Description 影院服务接口实现
 */
@Service
public class DefaultCinemaServiceApiImpl implements CinemaServiceAPI {

    @Autowired
    private MoocCinemaTMapper moocCinemaTMapper;

    @Autowired
    private MoocAreaDictTMapper moocAreaDictTMapper;

    @Autowired
    private MoocBrandDictTMapper moocBrandDictTMapper;

    @Autowired
    private MoocHallDictTMapper moocHallDictTMapper;

    @Autowired
    private MoocHallFilmInfoTMapper moocHallFilmInfoTMapper;

    @Autowired
    private MoocFieldTMapper moocFieldTMapper;

    /**
     * 获取指定条件下的影院列表
     *
     * @param cinemaQueryListDto
     * @return
     */
    @Override
    public CinemaPageVo getCinemas(CinemaQueryListDto cinemaQueryListDto) {

        EntityWrapper<MoocCinemaT> entityWrapper = new EntityWrapper<>();
        if (cinemaQueryListDto.getBrandId() != null && cinemaQueryListDto.getBrandId() != 99) {
            entityWrapper.eq("brand_id", cinemaQueryListDto.getBrandId());
        }
        if (cinemaQueryListDto.getHallType() != null && cinemaQueryListDto.getHallType() != 99) {
            entityWrapper.like("hall_ids", MessageFormat.format("#{0}#", cinemaQueryListDto.getHallType()));
        }
        if (cinemaQueryListDto.getDistrictId() != null && cinemaQueryListDto.getDistrictId() != 99) {
            entityWrapper.eq("area_id", cinemaQueryListDto.getDistrictId());
        }

        Page<MoocCinemaT> page = new Page<>(cinemaQueryListDto.getNowPage(), cinemaQueryListDto.getPageSize());

        //查询
        List<MoocCinemaT> moocCinemaTS = this.moocCinemaTMapper.selectPage(page, entityWrapper);

        //转换一下
        List<CinemaVo> cinemaVos = this.do2CinemaVos(moocCinemaTS);

        CinemaPageVo cinemaPageVo = new CinemaPageVo();
        cinemaPageVo.setNowPage(page.getCurrent());
        cinemaPageVo.setTotalPage((int) page.getPages());
        cinemaPageVo.setCinemas(cinemaVos);
        return cinemaPageVo;
    }

    /**
     * 获取所有影院品牌
     *
     * @return
     */
    @Override
    public List<BrandVo> getBrandVos() {
        List<MoocBrandDictT> moocBrandDictTS = this.moocBrandDictTMapper.selectList(null);
        return this.do2BrandVos(moocBrandDictTS);
    }

    /**
     * 获取所有影院区域地址
     *
     * @return
     */
    @Override
    public List<AreaVo> getAreaVos() {
        List<MoocAreaDictT> moocAreaDictTS = this.moocAreaDictTMapper.selectList(null);
        return this.do2AreaVos(moocAreaDictTS);
    }

    /**
     * 获取所有影厅音效实体
     *
     * @return
     */
    @Override
    public List<HallTypeVo> getHallTypeVos() {
        List<MoocHallDictT> moocHallDictTS = this.moocHallDictTMapper.selectList(null);
        return this.do2HallTypeVos(moocHallDictTS);
    }

    /**
     * 根据影院id获取对应的影院信息
     *
     * @param cinemaId
     * @return
     */
    @Override
    public CinemaInfoVo getCinemaInfoById(Integer cinemaId) {
        MoocCinemaT moocCinemaT = this.moocCinemaTMapper.selectById(cinemaId);
        return this.do2CinemaInfoVo(moocCinemaT);
    }

    /**
     * 根据影院id获取对应的电影和场次信息
     *
     * @param cinemaId
     * @return
     */
    @Override
    public List<FilmInfoVo> getFilmInfos(Integer cinemaId) {
        return this.moocHallFilmInfoTMapper.getFilmInfoVosByCinemaId(cinemaId);
    }

    /**
     * 根据放映场次id获取对应的电影信息
     *
     * @param fieldId
     * @return
     */
    @Override
    public FilmInfoVo getFilmInfoVo(Integer fieldId) {
        return this.moocHallFilmInfoTMapper.getFilmInfoVoByFieldId(fieldId);
    }

    /**
     * 根据放映场次id获取对应的影厅信息
     *
     * @param fieldId
     * @return
     */
    @Override
    public HallInfoVo getHallInfo(Integer fieldId) {
        return this.moocFieldTMapper.getHallInfoByFieldId(fieldId);
    }

    /**
     * 获取指定放映场次id对应的放映场次信息
     *
     * @param fieldId
     * @return
     */
    @Override
    public OrderQueryVo getFieldVoByFieldId(Integer fieldId) {
        MoocFieldT moocFieldT = this.moocFieldTMapper.selectById(fieldId);
        return do2FieldVo(moocFieldT);
    }

    /**
     * 放映场次实体转换
     *
     * @param moocFieldT
     * @return
     */
    private OrderQueryVo do2FieldVo(MoocFieldT moocFieldT) {
        OrderQueryVo orderQueryVo = new OrderQueryVo();
        orderQueryVo.setFieldId(moocFieldT.getUuid());
        orderQueryVo.setCinemaId(String.valueOf(moocFieldT.getCinemaId()));
        orderQueryVo.setFilmId(String.valueOf(moocFieldT.getFilmId()));
        orderQueryVo.setPrice(String.valueOf(moocFieldT.getPrice()));
        return orderQueryVo;
    }


    /**
     * 将影院明细数据库实体转换成对应的响应实体
     *
     * @param moocCinemaT
     * @return
     */
    private CinemaInfoVo do2CinemaInfoVo(MoocCinemaT moocCinemaT) {
        if (moocCinemaT != null) {
            CinemaInfoVo cinemaInfoVo = new CinemaInfoVo();
            cinemaInfoVo.setCinemaId(moocCinemaT.getUuid());
            cinemaInfoVo.setImgUrl(moocCinemaT.getImgAddress());
            cinemaInfoVo.setCinemaName(moocCinemaT.getCinemaName());
            cinemaInfoVo.setCinemaAddress(moocCinemaT.getCinemaAddress());
            cinemaInfoVo.setCinemaPhone(moocCinemaT.getCinemaPhone());
            return cinemaInfoVo;
        }
        return null;
    }

    /**
     * 将数据库实体转换成响应实体
     *
     * @param moocHallDictTS
     * @return
     */
    private List<HallTypeVo> do2HallTypeVos(List<MoocHallDictT> moocHallDictTS) {
        List<HallTypeVo> hallTypeVos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(moocHallDictTS)) {
            for (MoocHallDictT moocHallDictT : moocHallDictTS) {
                HallTypeVo hallTypeVo = new HallTypeVo();
                hallTypeVo.setHalltypeId(moocHallDictT.getUuid());
                hallTypeVo.setHalltypeName(moocHallDictT.getShowName());
                hallTypeVo.setActive(false);

                hallTypeVos.add(hallTypeVo);
            }
        }
        return hallTypeVos;
    }

    /**
     * 将数据库实体转换成响应实体 区域部分
     *
     * @param moocAreaDictTS
     * @return
     */
    private List<AreaVo> do2AreaVos(List<MoocAreaDictT> moocAreaDictTS) {
        List<AreaVo> areaVos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(moocAreaDictTS)) {
            for (MoocAreaDictT moocAreaDictT : moocAreaDictTS) {
                AreaVo areaVo = new AreaVo();
                areaVo.setAreaId(moocAreaDictT.getUuid());
                areaVo.setAreaName(moocAreaDictT.getShowName());
                areaVo.setActive(false);

                areaVos.add(areaVo);
            }
        }
        return areaVos;
    }


    /**
     * 将数据库的实体转换成响应实体
     *
     * @param moocBrandDictTS
     * @return
     */
    private List<BrandVo> do2BrandVos(List<MoocBrandDictT> moocBrandDictTS) {
        List<BrandVo> brandVos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(moocBrandDictTS)) {
            for (MoocBrandDictT moocBrandDictT : moocBrandDictTS) {
                BrandVo brandVo = new BrandVo();
                brandVo.setBrandId(moocBrandDictT.getUuid());
                brandVo.setBrandName(moocBrandDictT.getShowName());
                brandVo.setActive(false);

                brandVos.add(brandVo);
            }
        }
        return brandVos;
    }

    /**
     * 将数据库实体转换成响应实体
     *
     * @param moocCinemaTS
     * @return
     */
    private List<CinemaVo> do2CinemaVos(List<MoocCinemaT> moocCinemaTS) {
        List<CinemaVo> cinemaVos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(moocCinemaTS)) {
            for (MoocCinemaT moocCinemaT : moocCinemaTS) {
                CinemaVo cinemaVo = new CinemaVo();
                cinemaVo.setUuid(moocCinemaT.getUuid());
                cinemaVo.setCinemaName(moocCinemaT.getCinemaName());
                cinemaVo.setAddress(moocCinemaT.getCinemaAddress());
                cinemaVo.setMinimumPrice(moocCinemaT.getMinimumPrice());
                cinemaVos.add(cinemaVo);
            }
        }
        return cinemaVos;
    }

}
