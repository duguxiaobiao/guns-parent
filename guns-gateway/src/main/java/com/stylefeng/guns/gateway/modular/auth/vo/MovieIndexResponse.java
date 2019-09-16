package com.stylefeng.guns.gateway.modular.auth.vo;

import lombok.Data;

/**
 * @author ztkj-hzb
 * @Date 2019/8/30 14:43
 * @Description
 */
@Data
public class MovieIndexResponse<T> extends CommonRespose<T> {

    /**
     * 图片路径前缀
     */
    private String imgPre;

    /**
     * 成功响应
     *
     * @param data
     * @return
     */
    public static <T> MovieIndexResponse<T> success(String imgPre, T data) {
        MovieIndexResponse<T> movieResponse = new MovieIndexResponse<>();
        movieResponse.setStatus(0);
        movieResponse.setData(data);
        movieResponse.setImgPre(imgPre);
        return movieResponse;
    }

}
