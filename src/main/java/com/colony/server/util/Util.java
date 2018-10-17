package com.colony.server.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class Util {

    @Value("${token_prefix}")
    String token_prefix;
    @Value("${secret}")
    String token_secret;
    @Value("${expiration_time}")
    long expiration_time;

    private final Logger logger = LoggerFactory.getLogger(Util.class);

    /**
     * 从请求获得源ip
     *
     * @param request
     * @return
     */
    public String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")) {
                InetAddress inetAddress = null;
                try {
                    inetAddress = InetAddress.getLocalHost();
                    ip = inetAddress.getHostAddress();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }

        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }

    @CachePut(value = "ipList", key = "#ip")
    public String generateToken(String ip, JSONObject object) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("ip", ip);
        map.put("payload", object);

        String jwt = token_prefix + Jwts.builder()
                .setClaims(map)
                .setExpiration(new Date(System.currentTimeMillis() + expiration_time))
                .signWith(SignatureAlgorithm.HS256, token_secret)
                .compact();

        return jwt;
    }

    public Map<String, Object> parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(token_secret)
                .parseClaimsJws(token.replace(token_prefix, ""))
                .getBody();
    }

}
