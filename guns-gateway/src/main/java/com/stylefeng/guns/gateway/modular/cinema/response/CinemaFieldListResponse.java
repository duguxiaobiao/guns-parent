package com.stylefeng.guns.gateway.modular.cinema.response;

import com.stylefeng.guns.gateway.modular.auth.vo.CommonRespose;
import lombok.Data;

/**
 * @author ztkj-hzb
 * @Date 2019/9/17 13:53
 * @Description
 */
@Data
public class CinemaFieldListResponse<T> extends CommonRespose<T> {


    private String imgPre;

    /**
     * 成功响应实体
     * @param imgPre
     * @param data
     * @param <T>
     * @return
     */
    public static <T> CinemaFieldListResponse success(String imgPre, T data) {
        CinemaFieldListResponse<T> cinemaFieldListResponse = new CinemaFieldListResponse<>();
        cinemaFieldListResponse.setStatus(0);
        cinemaFieldListResponse.setImgPre(imgPre);
        cinemaFieldListResponse.setData(data);
        return cinemaFieldListResponse;
    }

}
