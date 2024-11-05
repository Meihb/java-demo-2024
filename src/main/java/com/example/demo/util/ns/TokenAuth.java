package com.example.demo.util.ns;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;


public class TokenAuth {
    private static final String TOKEN_ID = "xxx";
    private static final String TOKEN_SECRET = "xxxx";
    private static final String REST_URL = "xxx";
    private static final String CONSUMER_KEY = "xxx";
    private static final String CONSUMER_SECRET = "xxxx";
    private static final String REALM = "xxxx";
    private static final String CONTENT_TYPE = "content-type";
    private static final String APP_JSON = "application/json";
    private static final String EMPTY_JSON_PAYLOAD = "{}";
    private static final String VERSION = "1.0";

    private static OAuthService service = getService();
    private static Token accessToken = getToken();

    public static void main(String[] args) {
        Response responseGet = callWithHttpGet();
        System.out.println(responseGet.getMessage());
        System.out.println(responseGet.getCode());

        Response responsePost = callWithHttpPost();
        System.out.println(responsePost.getBody());
    }

    private static Response callWithHttpGet() {
        OAuthRequest request = new OAuthRequest(Verb.GET, REST_URL);
        request.setRealm(REALM);
        service.signRequest(accessToken, request);
        return request.send();
    }

    @SneakyThrows
    private static Response callWithHttpPost() {
        OAuthRequest request = new OAuthRequest(Verb.POST, REST_URL);
        request.setRealm(REALM);
        request.addHeader(CONTENT_TYPE, APP_JSON);
//        request.addPayload(EMPTY_JSON_PAYLOAD);

        ObjectMapper objectMapper = new ObjectMapper();
        // 创建一个匿名类来表示 JSON 数据结构
        String jsonString = objectMapper.writeValueAsString(new Object() {
            public String project_code = "123";
        });
        request.addPayload(jsonString);
        service.signRequest(accessToken, request);
        return request.send();
    }

    private static Token getToken() {
        return new Token(TOKEN_ID, TOKEN_SECRET);
    }

    private static OAuthService getService() {
        return new ServiceBuilder()
                .provider(NsApi.class)
                .apiKey(CONSUMER_KEY)
                .apiSecret(CONSUMER_SECRET)
                .signatureType(SignatureType.Header)
                .build();
    }

}