package com.example.demo.component;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.util.StreamUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author herbert.mei
 * @version 1.0
 * @date 2024/11/08 16:49
 */
public class CachedHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private byte[] requestBody;
    private Map<String, String[]> parameterMap;

    public CachedHttpServletRequestWrapper(HttpServletRequest request) {

        super(request);

        // 判断 Content-Type 是否为 x-www-form-urlencoded
        if ("application/x-www-form-urlencoded".equalsIgnoreCase(request.getContentType())) {
            parameterMap = new HashMap<>(request.getParameterMap());
            requestBody = null; // x-www-form-urlencoded 不缓存请求体
        } else {
            // application/json 等其他格式，缓存请求体
            try {
                requestBody = StreamUtils.copyToByteArray(request.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
                requestBody = new byte[0];
            }
            parameterMap = null;
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (requestBody == null) {
            return super.getInputStream(); // 对于 x-www-form-urlencoded，直接返回原始输入流
        }

        final ByteArrayInputStream bais = new ByteArrayInputStream(requestBody);
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return bais.available() == 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                // 默认实现为空
            }

            @Override
            public int read() throws IOException {
                return bais.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException {
        if (requestBody == null) {
            return super.getReader();
        }
        return new BufferedReader(new InputStreamReader(getInputStream(), StandardCharsets.UTF_8));
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        // 优先返回缓存的 parameterMap
        if (parameterMap != null) {
            return parameterMap;
        }
        return super.getParameterMap();
    }

    @Override
    public String getParameter(String name) {
        // 从缓存的 parameterMap 获取参数
        if (parameterMap != null) {
            String[] values = parameterMap.get(name);
            if (values != null && values.length > 0) {
                return values[0];
            }
            return null;
        }
        return super.getParameter(name);
    }

    @Override
    public String[] getParameterValues(String name) {
        // 从缓存的 parameterMap 获取参数
        if (parameterMap != null) {
            return parameterMap.get(name);
        }
        return super.getParameterValues(name);
    }
}
