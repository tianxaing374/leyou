package com.leyou.gateway.filters;

import com.leyou.auth.entity.UserInfo;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.common.utils.CookieUtils;
import com.leyou.gateway.config.FilterProperty;
import com.leyou.gateway.config.JwtProperties;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class AuthFilter extends ZuulFilter {

    @Autowired
    private FilterProperty filterProperty;

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE; //选择前置过滤
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER - 1; //在官方的过滤器之前
    }

    @Override
    public boolean shouldFilter() {
        //获取上下文
        RequestContext ctx = RequestContext.getCurrentContext();
        //获取request
        HttpServletRequest request = ctx.getRequest();
        //获取请求的url
        String path = request.getRequestURI();
        //判断是否放行
        return !isAllowPath(path);
    }

    private boolean isAllowPath(String path) {
        log.info("filterProperty.getAllowPaths():{}",filterProperty.getAllowPaths());
        for (String allowPath : filterProperty.getAllowPaths()) {
            if(path.startsWith(allowPath))
                return true;
        }
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        //完成过滤逻辑
        //获取上下文
        RequestContext ctx = RequestContext.getCurrentContext();
        //获取request
        HttpServletRequest request = ctx.getRequest();
        //获取cookie
        //Cookie[] cookies = request.getCookies();
        String cookieValue = CookieUtils.getCookieValue(request, jwtProperties.getCookieName());
        //解析cookie
        try {
            UserInfo user = JwtUtils.getUserInfo(jwtProperties.getPublicKey(), cookieValue);
            //todo 权限管理
        } catch (Exception e) {
            //拦截
            ctx.setSendZuulResponse(false); //进行拦截
            //403未授权
            ctx.setResponseStatusCode(403);
        }
        //校验权限
        return null;
    }
}
