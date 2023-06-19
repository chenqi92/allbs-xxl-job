package cn.allbs.job.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 自动注册注解
 *
 * @author ChenQi
 * @date 2023/6/19
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface XxlJobAuto {

    /**
     * 任务描述
     *
     * @return 任务描述
     */
    String jobDesc();

    /**
     * 负责人
     */
    String author();

    /**
     * 报警邮件，多个以逗号分隔
     */
    String alarmEmail() default "";

    /**
     * cron 表达式
     */
    String cron() default "";

    /**
     * 执行器任务参数
     */
    String executorParam() default "";

    /**
     * 失败重试次数
     */
    int executorFailRetryCount() default 0;

    /**
     * 执行超时时间
     */
    int executorTimeout() default 0;

    /**
     * 调度过期策略(忽略: DO_NOTHING 立即执行一次: FIRE_ONCE_NOW)
     */
    String misfireStrategy() default "DO_NOTHING";

    /**
     * 执行器路由策略 ()
     */
    String executorRouteStrategy() default "FIRST";

    /**
     * 调度状态 0 停止 1运行
     */
    int triggerStatus() default 0;

    /**
     * 固定速度调用时调用值 如果cron为空时使用此配置
     */
    long scheduleConf() default 1;

    /**
     * 固定速度时调用速度的单位, 默认为秒
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;
}
