package com.stylefeng.guns.gateway.modular.auth.filter;

import com.stylefeng.guns.core.base.tips.ErrorTip;
import com.stylefeng.guns.core.util.RenderUtil;
import com.stylefeng.guns.gateway.common.CurrentUser;
import com.stylefeng.guns.gateway.common.exception.BizExceptionEnum;
import com.stylefeng.guns.gateway.common.utils.UrlMatchUtil;
import com.stylefeng.guns.gateway.config.properties.JwtProperties;
import com.stylefeng.guns.gateway.modular.auth.util.JwtTokenUtil;
import io.jsonwebtoken.JwtException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 对客户端请求的jwt token验证过滤器
 *
 * @author fengshuonan
 * @Date 2017/8/24 14:04
 */
public class AuthFilter extends OncePerRequestFilter {

    private final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        /*if (request.getServletPath().equals("/" + jwtProperties.getAuthPath())) {
            chain.doFilter(request, response);
            return;
        }*/

        //处理忽略url
        if (this.isExistsInIgnoreUrls(request, response, chain)) {
            chain.doFilter(request, response);
            return;
        }

        final String requestHeader = request.getHeader(jwtProperties.getHeader());
        String authToken = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            authToken = requestHeader.substring(7);

            //校验token中是否包含了用户id
            String userId = jwtTokenUtil.getUsernameFromToken(authToken);

            if (StringUtils.isEmpty(userId)) {
                //没有用户id
                return;
            } else {
                //保存到当前线程中
                CurrentUser.saveUserId(userId);
            }


            //验证token是否过期,包含了验证jwt是否正确
            try {
                boolean flag = jwtTokenUtil.isTokenExpired(authToken);
                if (flag) {
                    RenderUtil.renderJson(response, new ErrorTip(BizExceptionEnum.TOKEN_EXPIRED.getCode(), BizExceptionEnum.TOKEN_EXPIRED.getMessage()));
                    return;
                }
            } catch (JwtException e) {
                //有异常就是token解析失败
                RenderUtil.renderJson(response, new ErrorTip(BizExceptionEnum.TOKEN_ERROR.getCode(), BizExceptionEnum.TOKEN_ERROR.getMessage()));
                return;
            }
        } else {
            //header没有带Bearer字段
            RenderUtil.renderJson(response, new ErrorTip(BizExceptionEnum.TOKEN_ERROR.getCode(), BizExceptionEnum.TOKEN_ERROR.getMessage()));
            return;
        }
        chain.doFilter(request, response);
    }


    /**
     * 获取忽略url的请求列表
     *
     * @return
     */
    private List<String> getIgnoreUrls() {
        //忽略列表
        List<String> ignoreUrlList = new ArrayList<>();
        //默认忽略的url
        ignoreUrlList.add("/" + jwtProperties.getAuthPath());
        //添加配置的忽略的url
        String ignoreUrls = jwtProperties.getIgnoreUrls();
        if (StringUtils.isNotBlank(ignoreUrls)) {
            String[] ignoreUrlArr = ignoreUrls.split(",");
            ignoreUrlList.addAll(Arrays.asList(ignoreUrlArr));
        }
        return ignoreUrlList;
    }

    /**
     * 判断当前请求url是否在忽略列表中
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    private boolean isExistsInIgnoreUrls(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String requestServletPath = request.getServletPath();
        //忽略列表
        List<String> ignoreUrlList = this.getIgnoreUrls();
        //判断当前请求是否需要跳过验证
        if (CollectionUtils.isNotEmpty(ignoreUrlList)) {
            for (int i = 0; i < ignoreUrlList.size(); i++) {
                //精准匹配
                /*if (requestServletPath.equals(ignoreUrlList.get(i))) {
                    return true;
                }*/
                //支持模糊匹配
                if(UrlMatchUtil.match(ignoreUrlList.get(i),requestServletPath)){
                    return true;
                }
            }
        }
        return false;
    }


}