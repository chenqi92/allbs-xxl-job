package cn.allbs.job.config;

import cn.allbs.job.annotation.XxlJobAuto;
import cn.allbs.job.model.XxlJobGroup;
import cn.allbs.job.model.XxlJobInfo;
import cn.allbs.job.service.JobGroupService;
import cn.allbs.job.service.JobInfoService;
import cn.hutool.core.collection.CollUtil;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 类 XxlJobAutoRegister
 *
 * @author ChenQi
 * @date 2023/6/19
 */
@Configuration(proxyBeanMethods = false)
@EnableAutoConfiguration
public class XxlJobAutoRegister implements ApplicationListener<ApplicationReadyEvent>,
        ApplicationContextAware {

    private static final Logger log = LoggerFactory.getLogger(XxlJobAutoRegister.class);

    @Resource
    private JobGroupService jobGroupService;

    @Resource
    private JobInfoService jobInfoService;

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        // 注册执行器
        this.addJobGroup();
        // 注册任务
        this.addJobInfo();
    }

    // 自动注册执行器
    private void addJobGroup() {
        if (jobGroupService.preciselyCheck()) {
            return;
        }
        if (jobGroupService.autoRegisterGroup()) {
            log.info("auto register xxl-job group success!");
        }
    }

    private void addJobInfo() {
        List<XxlJobGroup> jobGroups = jobGroupService.getJobGroup();
        if (CollUtil.isEmpty(jobGroups)) {
            log.error("执行器不存在，自动注册失败!");
        }
        XxlJobGroup xxlJobGroup = jobGroups.get(0);

        String[] beanDefinitionNames = applicationContext.getBeanNamesForType(Object.class, false, true);
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = applicationContext.getBean(beanDefinitionName);

            Map<Method, XxlJob> annotatedMethods = MethodIntrospector.selectMethods(bean.getClass(),
                    (MethodIntrospector.MetadataLookup<XxlJob>) method -> AnnotatedElementUtils.findMergedAnnotation(method, XxlJob.class));
            for (Map.Entry<Method, XxlJob> methodXxlJobEntry : annotatedMethods.entrySet()) {
                Method executeMethod = methodXxlJobEntry.getKey();
                XxlJob xxlJob = methodXxlJobEntry.getValue();

                //自动注册
                if (executeMethod.isAnnotationPresent(XxlJobAuto.class)) {
                    XxlJobAuto xxlJobAuto = executeMethod.getAnnotation(XxlJobAuto.class);
                    List<XxlJobInfo> jobInfo = jobInfoService.getJobInfo(xxlJobGroup.getId(), xxlJob.value());
                    if (!jobInfo.isEmpty()) {
                        //因为是模糊查询，需要再判断一次
                        Optional<XxlJobInfo> first = jobInfo.stream()
                                .filter(xxlJobInfo -> xxlJobInfo.getExecutorHandler().equals(xxlJob.value()))
                                .findFirst();
                        if (first.isPresent())
                            continue;
                    }

                    XxlJobInfo xxlJobInfo = createXxlJobInfo(xxlJobGroup, xxlJob, xxlJobAuto);
                    Integer jobInfoId = jobInfoService.addJobInfo(xxlJobInfo);
                }
            }
        }
    }

    private XxlJobInfo createXxlJobInfo(XxlJobGroup xxlJobGroup, XxlJob xxlJob, XxlJobAuto xxlJobAuto) {
        XxlJobInfo xxlJobInfo = new XxlJobInfo();
        xxlJobInfo.setJobGroup(xxlJobGroup.getId());
        xxlJobInfo.setJobDesc(xxlJobAuto.jobDesc());
        xxlJobInfo.setAuthor(xxlJobAuto.author());
        if (StringUtils.hasText(xxlJobAuto.cron())) {
            xxlJobInfo.setScheduleType("CRON");
            xxlJobInfo.setScheduleConf(xxlJobAuto.cron());
        } else {
            xxlJobInfo.setScheduleType("FIX_RATE");
            // 时间
            xxlJobInfo.setScheduleConf(String.valueOf(TimeUnit.SECONDS.convert(xxlJobAuto.scheduleConf(), xxlJobAuto.timeUnit())));
        }
        xxlJobInfo.setGlueType("BEAN");
        xxlJobInfo.setExecutorHandler(xxlJob.value());
        xxlJobInfo.setExecutorRouteStrategy(xxlJobAuto.executorRouteStrategy());
        xxlJobInfo.setMisfireStrategy(xxlJobAuto.misfireStrategy());
        xxlJobInfo.setExecutorBlockStrategy("SERIAL_EXECUTION");
        xxlJobInfo.setExecutorTimeout(xxlJobAuto.executorTimeout());
        xxlJobInfo.setExecutorFailRetryCount(xxlJobAuto.executorFailRetryCount());
        xxlJobInfo.setGlueRemark("GLUE代码初始化");
        xxlJobInfo.setTriggerStatus(xxlJobAuto.triggerStatus());
        xxlJobInfo.setExecutorParam(xxlJobInfo.getExecutorParam());

        return xxlJobInfo;
    }
}
