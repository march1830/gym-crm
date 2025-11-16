package com.yourcompany.gym.config.interceptors;

import org.slf4j.MDC;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import java.io.IOException;

public class MdcHeaderInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        String transactionId = MDC.get("transactionId");
        if (transactionId != null) {
            request.getHeaders().add("X-Transaction-Id", transactionId);
        }
        return execution.execute(request, body);
    }
}
