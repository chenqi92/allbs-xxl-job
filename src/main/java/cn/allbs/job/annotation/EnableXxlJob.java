package cn.allbs.job.annotation;

import cn.allbs.job.XxlJobAutoConfiguration;
import cn.allbs.job.config.XxlJobAutoRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author ChenQi
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({XxlJobAutoConfiguration.class, XxlJobAutoRegister.class})
public @interface EnableXxlJob {
}
