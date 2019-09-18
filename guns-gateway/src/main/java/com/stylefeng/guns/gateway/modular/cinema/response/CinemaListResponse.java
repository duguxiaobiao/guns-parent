package com.stylefeng.guns.gateway.modular.cinema.response;

import com.stylefeng.guns.gateway.modular.auth.vo.CommonRespose;
import lombok.Data;

/**
 * @author ztkj-hzb
 * @Date 2019/9/16 16:36
 * @Description 影院列表查询信息响应格式
 */
@Data
public class CinemaListResponse<T> extends CommonRespose<T> {


    private Integer nowPage;

    private Integer totalPage;

    /**
     * 成功响应实体
     *
     * @param nowPage
     * @param totalPage
     * @param data
     * @param <T>
     * @return
     */
    public static <T> CinemaListResponse success(Integer nowPage, Integer totalPage, T data) {
        CinemaListResponse<T> cinemaListResponse = new CinemaListResponse<>();
        cinemaListResponse.setStatus(0);
        cinemaListResponse.setNowPage(nowPage);
        cinemaListResponse.setTotalPage(totalPage);
        cinemaListResponse.setData(data);
        return cinemaListResponse;
    }

}
