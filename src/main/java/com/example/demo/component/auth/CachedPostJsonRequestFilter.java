package com.example.demo.component.auth;

import com.alibaba.fastjson2.JSON;
import com.example.demo.component.CachedHttpServletRequestWrapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;
/**
 * 框架中做请求的拦截处理
 * <p>
 * 未添加{@code  @WebFilter}，不让@ServletComponentScan 扫描，采用spring 加载servlet filter，可以调整顺序，
 * <p>
 * 比如可以早于spring security filter的顺序
 *
 * @author hdf
 */
@Slf4j
public class CachedPostJsonRequestFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        // 添加调用链响应头,因为spring 会在filter中结束respone 所以提前加上
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        // 检查请求的 Content-Type 是否为 application/json
        String contentType = httpServletRequest.getContentType();
        if (contentType != null && contentType.toLowerCase().contains("application/json")) {
            // 如果是 JSON 请求，使用 CachedHttpServletRequestWrapper
            CachedHttpServletRequestWrapper wrappedRequest = new CachedHttpServletRequestWrapper(httpServletRequest);
            if (log.isDebugEnabled()) {
                // form 参数打印
                Map<String, String[]> parameterMap = wrappedRequest.getParameterMap();
                if (!parameterMap.isEmpty()) {
                    log.debug("Uri=>{} Request Params=>{}", wrappedRequest.getRequestURI(),
                            JSON.toJSONString(parameterMap));
                }
            }
            chain.doFilter(wrappedRequest, response); // 使用包装后的请求对象继续处理
        } else {
            if (log.isDebugEnabled()) {
                // form 参数打印
                Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
                if (!parameterMap.isEmpty()) {
                    log.debug("Uri=>{} Request Params=>{}", httpServletRequest.getRequestURI(),
                            JSON.toJSONString(parameterMap));
                }
            }
            // 如果不是 JSON 请求，直接传递原始请求
            chain.doFilter(request, response);
        }
    }
}
