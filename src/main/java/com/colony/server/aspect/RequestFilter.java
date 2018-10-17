package com.colony.server.aspect;

import com.colony.server.exception.TokenException;
import com.colony.server.util.Util;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.regex.Pattern;

@Component
@Aspect
public class RequestFilter {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Value("${header_string}")
    String header;

    @Value("${key_prefix}")
    String key_prefix;

    @Autowired
    Util util;
    @Autowired
    StringRedisTemplate template;

    @Around(value = "execution(* com.colony.server.controller.*.*(..))&&args(request,..)")
    public Object validateRequest(ProceedingJoinPoint proceedingJoinPoint, HttpServletRequest request) throws Throwable {
        String uri = request.getRequestURI();
        logger.info("validating request : {}", uri);

        if (!inWhiteList(uri)) {
            String token = request.getHeader(header);
            //判断token是否为空
            //解析token内容
            if (token != null && !token.equals("null")) {
                Map<String, Object> map = null;
                try {
                    map = util.parseToken(token);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new TokenException();
                }

                String ip = (String) map.get("ip");
                String val = template.opsForValue().get(key_prefix + ip);
                if (val != null && val.equals(token)) {

                } else {
                    throw new TokenException();
                }
            } else {
                throw new TokenException();
            }
        }

        Object res = proceedingJoinPoint.proceed();

        return res;
    }

    private boolean inWhiteList(String uri) {
        String regx = "/register";
        return Pattern.matches(regx, uri);
    }
}
