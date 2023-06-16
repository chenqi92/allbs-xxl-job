package cn.allbs.job.annotation;

import cn.allbs.job.XxlJobAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author ChenQi
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({XxlJobAutoConfiguration.class})
public @interface EnableXxlJob {
}
