package com.example.demo.entity.log;

import lombok.Data;

import java.net.InetAddress;
import java.util.Map;

@Data
public class HttpRequestLog {
    public String method;
    public String uri;
    public String Host;
    public String Scheme;
    public InetAddress RemoteAddr;
    public Map<String, String[]> parameters;
    public Map<String, String> headers;
}
