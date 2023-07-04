package cn.allbs.job.properties;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author ChenQi
 */
@Component
@ConfigurationProperties(prefix = "xxl.job")
public class XxlJobProperties implements InitializingBean, EnvironmentAware {

    private Environment environment;

    private XxlAdminProperties admin = new XxlAdminProperties();

    private XxlExecutorProperties executor = new XxlExecutorProperties();

    public XxlJobProperties() {
    }

    @Override
    public void afterPropertiesSet() {
        // 若是没有设置appName 则取 application Name
        if (!StringUtils.hasText(executor.getAppname())) {
            executor.setAppname(environment.getProperty("spring.application.name"));
        }
    }

    /**
     * Set the {@code Environment} that this component runs in.
     */
    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public Environment getEnvironment() {
        return this.environment;
    }

    public XxlAdminProperties getAdmin() {
        return this.admin;
    }

    public XxlExecutorProperties getExecutor() {
        return this.executor;
    }

    public void setAdmin(XxlAdminProperties admin) {
        this.admin = admin;
    }

    public void setExecutor(XxlExecutorProperties executor) {
        this.executor = executor;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof XxlJobProperties)) return false;
        final XxlJobProperties other = (XxlJobProperties) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$environment = this.getEnvironment();
        final Object other$environment = other.getEnvironment();
        if (this$environment == null ? other$environment != null : !this$environment.equals(other$environment))
            return false;
        final Object this$admin = this.getAdmin();
        final Object other$admin = other.getAdmin();
        if (this$admin == null ? other$admin != null : !this$admin.equals(other$admin)) return false;
        final Object this$executor = this.getExecutor();
        final Object other$executor = other.getExecutor();
        if (this$executor == null ? other$executor != null : !this$executor.equals(other$executor)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof XxlJobProperties;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $environment = this.getEnvironment();
        result = result * PRIME + ($environment == null ? 43 : $environment.hashCode());
        final Object $admin = this.getAdmin();
        result = result * PRIME + ($admin == null ? 43 : $admin.hashCode());
        final Object $executor = this.getExecutor();
        result = result * PRIME + ($executor == null ? 43 : $executor.hashCode());
        return result;
    }

    public String toString() {
        return "XxlJobProperties(environment=" + this.getEnvironment() + ", admin=" + this.getAdmin() + ", executor=" + this.getExecutor() + ")";
    }
}
