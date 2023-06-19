package cn.allbs.job.service.impl;

import cn.allbs.job.properties.XxlJobProperties;
import cn.allbs.job.service.JobLoginService;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.HttpCookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author : Hydra
 * @date: 2022/9/19 17:49
 * @version: 1.0
 */
@Service
@EnableConfigurationProperties({XxlJobProperties.class})
public class JobLoginServiceImpl implements JobLoginService {

    @Resource
    private XxlJobProperties xxlJobProperties;

    private final Map<String, String> loginCookie = new HashMap<>();

    @Override
    public void login() {
        String url = xxlJobProperties.getAdmin().getAddress() + "login";
        HttpResponse response = HttpRequest.post(url)
                .form("userName", xxlJobProperties.getAdmin().getUsername())
                .form("password", xxlJobProperties.getAdmin().getPassword())
                .execute();
        List<HttpCookie> cookies = response.getCookies();
        Optional<HttpCookie> cookieOpt = cookies.stream()
                .filter(cookie -> cookie.getName().equals("XXL_JOB_LOGIN_IDENTITY")).findFirst();
        if (!cookieOpt.isPresent())
            throw new RuntimeException("get xxl-job cookie error!");

        String value = cookieOpt.get().getValue();
        loginCookie.put("XXL_JOB_LOGIN_IDENTITY", value);
    }

    @Override
    public String getCookie() {
        for (int i = 0; i < 3; i++) {
            String cookieStr = loginCookie.get("XXL_JOB_LOGIN_IDENTITY");
            if (cookieStr != null) {
                return "XXL_JOB_LOGIN_IDENTITY=" + cookieStr;
            }
            login();
        }
        throw new RuntimeException("get xxl-job cookie error!");
    }


}
