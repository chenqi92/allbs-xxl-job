package cn.allbs.job.properties;

import lombok.Data;

/**
 * @author ChenQi
 */
@Data
public class XxlAdminProperties {

    /**
     * 调度中心部署跟地址 [选填]：如调度中心集群部署存在多个地址则用逗号分隔。 执行器将会使用该地址进行"执行器心跳注册"和"任务结果回调"；为空则关闭自动注册；
     */
    private String address;

    /**
     * 用户名(自动注册使用)
     */
    private String username;

    /**
     * 密码(自动注册使用)
     */
    private String password;

}
