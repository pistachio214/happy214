package com.happy.lucky.framework.filter;

import com.happy.lucky.framework.wrapper.BodyReaderHttpServletRequestWrapper;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 过滤器，处理请求初期的request数据流使用一次就失效的问题
 * 第一个进行过滤
 *
 * @author songyangpeng
 */
@Component
@WebFilter(filterName = "bodyReaderFilter", urlPatterns = "/*")
@Order(1)
public class BodyReaderFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        ServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper(httpServletRequest);
        filterChain.doFilter(requestWrapper, servletResponse);
    }

    @Override
    public void destroy() {
    }

}
