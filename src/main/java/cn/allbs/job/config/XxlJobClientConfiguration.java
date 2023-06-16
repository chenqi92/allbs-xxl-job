package cn.allbs.job.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author ChenQi
 */
@Configuration
@PropertySource(factory = YamlPropertySourceFactory.class, value = "classpath:xxl-job-client.yml")
public class XxlJobClientConfiguration {
}
