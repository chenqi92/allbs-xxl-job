package cn.allbs.job.service.impl;

import cn.allbs.job.model.XxlJobGroup;
import cn.allbs.job.properties.XxlJobProperties;
import cn.allbs.job.service.JobGroupService;
import cn.allbs.job.service.JobLoginService;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author : Hydra
 * @date: 2022/9/19 17:34
 * @version: 1.0
 */
@Service
@AutoConfigureAfter({JobLoginService.class})
@EnableConfigurationProperties({XxlJobProperties.class})
public class JobGroupServiceImpl implements JobGroupService {

    @Resource
    private XxlJobProperties xxlJobProperties;

    @Resource
    private JobLoginService jobLoginService;

    @Override
    public List<XxlJobGroup> getJobGroup() {
        String url = xxlJobProperties.getAdmin().getAddress() + "jobgroup/pageList";
        HttpResponse response = HttpRequest.post(url).form("appname", xxlJobProperties.getExecutor().getAppname()).cookie(jobLoginService.getCookie()).execute();

        String body = response.body();
        JSONArray array = JSONUtil.parse(body).getByPath("data", JSONArray.class);
        List<XxlJobGroup> list = array.stream().map(o -> JSONUtil.toBean((JSONObject) o, XxlJobGroup.class)).collect(Collectors.toList());

        return list;
    }

    @Override
    public boolean autoRegisterGroup() {
        String url = xxlJobProperties.getAdmin().getAddress() + "jobgroup/save";
        HttpRequest httpRequest = HttpRequest.post(url).form("appname", xxlJobProperties.getExecutor().getAppname()).form("title", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "注册");

        httpRequest.form("addressType", xxlJobProperties.getExecutor().getAutoRegister());
        if (xxlJobProperties.getExecutor().getAutoRegister().equals(1)) {
            if (!StringUtils.hasText(xxlJobProperties.getExecutor().getManualAddressList())) {
                throw new RuntimeException("手动录入模式下,执行器地址列表不能为空");
            }
            httpRequest.form("addressList", xxlJobProperties.getExecutor().getManualAddressList());
        }

        HttpResponse response = httpRequest.cookie(jobLoginService.getCookie()).execute();
        Object code = JSONUtil.parse(response.body()).getByPath("code");
        return code.equals(200);
    }

    @Override
    public boolean preciselyCheck() {
        List<XxlJobGroup> jobGroup = getJobGroup();
        Optional<XxlJobGroup> has = jobGroup.stream().filter(xxlJobGroup -> xxlJobGroup.getAppname().equals(xxlJobProperties.getExecutor().getAppname())).findAny();
        return has.isPresent();
    }

}
