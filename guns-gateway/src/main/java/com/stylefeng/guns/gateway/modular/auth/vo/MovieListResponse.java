package com.stylefeng.guns.gateway.modular.auth.vo;

/**
 * @author ztkj-hzb
 * @Date 2019/9/11 15:22
 * @Description
 */
public class MovieListResponse<T> extends CommonRespose<T> {

    /**
     * 图片地址
     */
    private String imgPre;
    /**
     * 当前第几页
     */
    private Integer nowPage;
    /**
     * 总页数
     */
    private Integer totalPage;


    public static <T> MovieListResponse<T> success(String imgPre, Integer nowPage, Integer totalPage, T data) {
        MovieListResponse<T> movieListResponse = new MovieListResponse<>();
        movieListResponse.setStatus(0);
        movieListResponse.setImgPre(imgPre);
        movieListResponse.setNowPage(nowPage);
        movieListResponse.setTotalPage(totalPage);
        movieListResponse.setData(data);
        return movieListResponse;
    }


    public String getImgPre() {
        return imgPre;
    }

    public void setImgPre(String imgPre) {
        this.imgPre = imgPre;
    }

    public Integer getNowPage() {
        return nowPage;
    }

    public void setNowPage(Integer nowPage) {
        this.nowPage = nowPage;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }
}
