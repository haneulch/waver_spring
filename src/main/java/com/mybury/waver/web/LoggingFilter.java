package com.mybury.waver.web;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

@Slf4j
public class LoggingFilter extends OncePerRequestFilter {

    private static final int MAX_LOG_BYTES = 10 * 1024;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        long start = System.currentTimeMillis();
        ContentCachingRequestWrapper reqWrapper = new ContentCachingRequestWrapper(request);

        try {
            filterChain.doFilter(reqWrapper, response);
        } finally {
            Map<String, String> params = new LinkedHashMap<>();
            request.getParameterMap().forEach(
                (k, v) -> params.put(k, (v == null) ? null : String.join(",", v)));

            String reqBody = "";
            byte[] rb = reqWrapper.getContentAsByteArray();
            if (rb.length > 0) {
                int len = Math.min(rb.length, MAX_LOG_BYTES);
                reqBody = new String(rb, 0, len, StandardCharsets.UTF_8);
            }

            log.info("REQ ==> [{}]{} took {}ms",
                request.getMethod(), request.getRequestURI(), System.currentTimeMillis() - start);

            if (!params.isEmpty()) {
                log.info("params ==> {}", params);
            }

            String bodyText = getBodyData(reqBody);
            if (StringUtils.hasText(reqBody)) {
                log.info("body ==> {}", bodyText);
            }
        }
    }

    private String getBodyData(String in) {
        if (in == null) {
            return "";
        }
        String s = in.replaceAll("\\s+", " ");
        if (s.length() > 5000) {
            return s.substring(0, 5000) + "...(truncated)";
        }
        return s;
    }
}
