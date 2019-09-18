package com.stylefeng.guns.gateway.modular.cinema;

import com.stylefeng.guns.api.cinema.CinemaServiceAPI;
import com.stylefeng.guns.api.cinema.model.dto.CinemaQueryListDto;
import com.stylefeng.guns.api.cinema.model.vo.*;
import com.stylefeng.guns.core.util.ExceptionUtil;
import com.stylefeng.guns.gateway.modular.auth.vo.CommonRespose;
import com.stylefeng.guns.gateway.modular.cinema.qo.CinemaListQo;
import com.stylefeng.guns.gateway.modular.cinema.response.CinemaFieldListResponse;
import com.stylefeng.guns.gateway.modular.cinema.response.CinemaListResponse;
import com.stylefeng.guns.gateway.modular.cinema.vo.CinemaConditionVo;
import com.stylefeng.guns.gateway.modular.cinema.vo.CinemaFieldInfoVo;
import com.stylefeng.guns.gateway.modular.cinema.vo.CinemaFieldListVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ztkj-hzb
 * @Date 2019/9/16 15:32
 * @Description 影院网关服务接口
 */
@RestController
@RequestMapping("/cinema")
@Slf4j
public class CinemaController {

    /**
     * cache开启缓存，使用lru：删除最少最近使用原则
     */
    @Reference(cache = "lru")
    private CinemaServiceAPI cinemaServiceAPI;

    /**
     * 查询影院列表，根据条件查询所有影院
     *
     * @param cinemaListQo
     * @return
     */
    @GetMapping("/getCinemas")
    public CommonRespose getCinemas(CinemaListQo cinemaListQo) {
        try {
            //封装入参
            CinemaQueryListDto cinemaQueryListDto = new CinemaQueryListDto();
            BeanUtils.copyProperties(cinemaListQo, cinemaQueryListDto);

            //调用接口根据条件查询
            CinemaPageVo cinemas = this.cinemaServiceAPI.getCinemas(cinemaQueryListDto);
            if (cinemas == null || CollectionUtils.isEmpty(cinemas.getCinemas())) {
                return CommonRespose.businessFail("没有对应的影院信息");
            }

            //响应
            return CinemaListResponse.success(cinemas.getNowPage(), cinemas.getTotalPage(), cinemas.getCinemas());
        } catch (BeansException e) {
            log.error("查询影院列表出现异常， 异常原因：{}", ExceptionUtil.getStackTrace(e));
            return CommonRespose.businessFail("影院信息查询失败");
        }
    }


    /**
     * 获取影院列表查询条件
     *
     * @param brandId
     * @param hallType
     * @param areaId
     * @return
     */
    @GetMapping("/getCondition")
    public CommonRespose getCondition(@RequestParam(name = "brandId", required = false, defaultValue = "99") Integer brandId,
                                      @RequestParam(name = "hallType", required = false, defaultValue = "99") Integer hallType,
                                      @RequestParam(name = "areaId", required = false, defaultValue = "99") Integer areaId) {

        try {
            List<BrandVo> brandVos = this.cinemaServiceAPI.getBrandVos();
            brandVos = brandVos.stream().peek(temp -> {
                if (temp.getBrandId().equals(brandId)) {
                    temp.setActive(true);
                }
            }).collect(Collectors.toList());

            List<AreaVo> areaVos = this.cinemaServiceAPI.getAreaVos();
            areaVos = areaVos.stream().peek(temp -> {
                if (temp.getAreaId().equals(areaId)) {
                    temp.setActive(true);
                }
            }).collect(Collectors.toList());

            List<HallTypeVo> hallTypeVos = this.cinemaServiceAPI.getHallTypeVos();
            hallTypeVos = hallTypeVos.stream().peek(temp -> {
                if (temp.getHalltypeId().equals(hallType)) {
                    temp.setActive(true);
                }
            }).collect(Collectors.toList());

            CinemaConditionVo cinemaConditionVo = new CinemaConditionVo();
            cinemaConditionVo.setBrandList(brandVos);
            cinemaConditionVo.setAreaList(areaVos);
            cinemaConditionVo.setHalltypeList(hallTypeVos);

            return CommonRespose.success(cinemaConditionVo);
        } catch (Exception e) {
            log.error("获取影院列表查询条件出现异常，异常原因：{}",ExceptionUtil.getStackTrace(e));
            return CommonRespose.businessFail("影院信息查询失败");
        }
    }


    /**
     * 获取播放场次接口
     *
     * @param cinemaId 影院编号，必填
     * @return
     */
    @RequestMapping("/getFields")
    public CommonRespose getFields(Integer cinemaId) {

        try {
            //1.获取指定影院的信息
            CinemaInfoVo cinemaInfoById = this.cinemaServiceAPI.getCinemaInfoById(cinemaId);

            //2.获取指定影院的电影场次列表
            List<FilmInfoVo> filmInfos = this.cinemaServiceAPI.getFilmInfos(cinemaId);

            //3.组装响应
            CinemaFieldListVo cinemaFieldListVo = new CinemaFieldListVo();
            cinemaFieldListVo.setCinemaInfoVo(cinemaInfoById);
            cinemaFieldListVo.setFilmList(filmInfos);

            return CinemaFieldListResponse.success("http://img.meetingshop.cn/", cinemaFieldListVo);
        } catch (Exception e) {
            log.error("获取播放场次接口出现异常，异常原因：{}",ExceptionUtil.getStackTrace(e));
            return CommonRespose.businessFail("影院信息查询失败");
        }
    }


    /**
     * 获取场次详细信息接口
     *
     * @param cinemaId 影院编号
     * @param fieldId  场次编号
     * @return
     */
    @RequestMapping("/getFieldInfo")
    public CommonRespose getFieldInfo(Integer cinemaId, Integer fieldId) {

        try {
            //1.根据影院id获取对应的影院信息
            CinemaInfoVo cinemaInfoById = this.cinemaServiceAPI.getCinemaInfoById(cinemaId);

            //2.根据场次信息获取对应的电影信息
            FilmInfoVo filmInfoVo = this.cinemaServiceAPI.getFilmInfoVo(fieldId);

            //3.根据场次信息获取对应的影厅信息
            HallInfoVo hallInfo = this.cinemaServiceAPI.getHallInfo(fieldId);

            //4.组装响应
            CinemaFieldInfoVo cinemaFieldInfoVo = new CinemaFieldInfoVo();
            cinemaFieldInfoVo.setFilmInfoVo(filmInfoVo);
            cinemaFieldInfoVo.setCinemaInfoVo(cinemaInfoById);
            cinemaFieldInfoVo.setHallInfoVo(hallInfo);

            return CinemaFieldListResponse.success("http://img.meetingshop.cn/", cinemaFieldInfoVo);
        } catch (Exception e) {
            log.error("获取场次详细信息接口出现异常，异常原因：{}",ExceptionUtil.getStackTrace(e));
            return CommonRespose.businessFail("影院信息查询失败");
        }
    }

}
