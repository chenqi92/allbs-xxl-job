## 依赖jar包
| 引入包             | 版本    |
| ------------------ | ------- |
| jdk                | 1.8     |
| spring boot        | 2.7.2   |
| xxl-job-core | 2.3.1 |
| allbs-common | 2.0.0 |

## 使用
### 添加依赖

{% tabs tag-hide %}
<!-- tab maven -->

```xml
<dependency>
  <groupId>cn.allbs</groupId>
  <artifactId>allbs-xxl-job</artifactId>
  <version>2.0.1</version>
</dependency>
```

<!-- endtab -->

<!-- tab Gradle -->

```
implementation 'cn.allbs:allbs-xxl-job:2.0.1'
```

<!-- endtab -->

<!-- tab Kotlin -->

```
implementation("cn.allbs:allbs-xxl-job:2.0.1")
```

<!-- endtab -->
{% endtabs %}

### 启动xxl-job调度中心

![](https://nas.allbs.cn:9006/cloudpic/202109161754571.png)

### 配置需要调度动态定时任务的服务

启动类添加注解{% label @EnableXxlJob red %} 开启动态定时任务

### 添加配置或者只添加环境变量，环境 变量如下图

```yaml
xxl:
  job:
    admin:
      # 注册中心地址
      address: http://${XXL_JOB_SERVER_HOST:xxl-job-server-host}:${XXL_JOB_SERVER_PORT:8080}/${XXL_JOB_CONTEXT_PATH:xxl-job-admin}/
      # 自动注册时使用的账号
      username: ${XXL_JOB_SERVER_USERNAME:admin}
      # 自动注册时使用的密码
      password: ${XXL_JOB_SERVER_PASSWORD:123456}
    executor:
      # 执行器名称，如果为空则使用项目的application名称
      appname: ${XXL_JOB_EXECUTOR:xxl-job-executor-client}
      # 执行器端口
      port: ${XXL_JOB_CLIENT_PORT:9001}
      # 日志路径
      logpath: ${XXL_JOB_LOG_PATH:/data/applogs/xxl-job/}
      # token
      access-token: ${XXL_JOB_ACCESS_TOKEN:default_token}
      # 是否自动注册 0为自动注册， 1为手动注册
      auto-register: ${XXL_JOB_EXECUTOR_AUTO:0}
      # 手动注册列表
      manual-address-list:
        - http://127.0.0.1:9999
```
### yml实际配置示例

![image-20230619161135967](https://nas.allbs.cn:9006/cloudpic/2023/06/c695759df1fcd5f7be31bf07ae69c12a.png)

### 注意点

![image-20230316163457133](https://nas.allbs.cn:9006/cloudpic/2023/03/22e1c5cb3d46a411eb4fd66c169a3448.png)

![image-20230316162908400](https://nas.allbs.cn:9006/cloudpic/2023/03/a186a1e2e263e5d51673729a701816e8.png)

*想要成功注册必须满足以下条件，否则会出现`xxl-job registry fail`*

- xxl-job调度中心如果设置了content-path,则客户端必须添加该字符串，即xxl.job.admin.address中的内容
- xxl-job rpc接口调用中url拼接默认没有斜杠，所以必须在content-path之后添加斜杠
- 如果xxl-job调度中心配置了accessToken则客户端也需要配置

示例

![image-20230316163820639](https://nas.allbs.cn:9006/cloudpic/2023/03/12ba4adc95b2cbebe7a1fcc4613da80b.png)

### 添加环境变量

|      环境变量名称      |         默认值          |                        说明                        |
| :--------------------: | :---------------------: | :------------------------------------------------: |
| `XXL_JOB_SERVER_HOST`  |   xxl-job-server-host   | 调度中心的ip,如果使用默认则需在hosts中配置对呀的ip |
| `XXL_JOB_SERVER_PORT`  |          8080           |                     调度中心ip                     |
| `XXL_JOB_SERVER_USERNAME` | admin | 自动注册时使用的账号 |
| `XXL_JOB_SERVER_PASSWORD` | 123456 | 自动注册时使用的密码 |
| `XXL_JOB_CONTEXT_PATH` |      xxl-job-admin      |                调度中心content-path                |
|   `XXL_JOB_EXECUTOR`   | xxl-job-executor-client |                     执行器名称                     |
| `XXL_JOB_CLIENT_PORT`  |          9001           | 客户端端口，目前会导致多个客户端端口冲突，后续优化 |
|   `XXL_JOB_LOG_PATH`   | /data/applogs/xxl-job/  |                    日志保存路径                    |
| `XXL_JOB_ACCESS_TOKEN` |      default_token      |             调度中心配置的accessToken              |
| `XXL_JOB_EXECUTOR_AUTO` | 0 | 是否自动注册 0为自动注册， 1为手动注册|
| `manual-address-list` | http://127.0.0.1:9999,http://127.0.0.1:8888 | 手动注册列表 以逗号分隔 |


### 普通使用，需要在服务端的管理页面中手动注册和启动

```java
@Slf4j
@Component
public class DemoJob {
  // 这个注解中的内容就是jobHandler
  @XxlJob("demoJobHandler")
  public ReturnT<String> demoJobHandler(String s) {
     XxlJobHelper.log("This is a demo job." + XxlJobHelper.getShardIndex());
     return SUCCESS;
  }
}
```

### 手动注册启动

![](https://nas.allbs.cn:9006/cloudpic/202109161758213.png)

### 自动注册和使用(参考[文章](https://juejin.cn/post/7216604684035325989))

需要自动注册的方法需要添加`@XxlJobAuto`注解



```java
import cn.allbs.job.annotation.XxlJobAuto;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author ChenQi
 */
@Slf4j
@Component
public class DemoJob {

    /**
     * 使用cron,不自动启动的情况
     * @param s
     * @return
     */
    @XxlJob("demoJobHandler")
    @XxlJobAuto(jobDesc = "根据cron指定频率运行, 自动注册后不启动", cron = "* * 0/1 * * ? ", author = "chenqi")
    public ReturnT<String> demoJobHandler(String s) {
        XxlJobHelper.log("This is a demo job." + XxlJobHelper.getShardIndex());

        return ReturnT.SUCCESS;
    }

    /**
     * 使用固定频率，注册后自动启动的情况
     * @param s
     * @return
     */
    @XxlJob("demoJobHandlerAuto")
    @XxlJobAuto(jobDesc = "固定速度测试，注册后自动启动", author = "chenqi", scheduleConf = 1, timeUnit = TimeUnit.MINUTES, triggerStatus = 1)
    public ReturnT<String> demoJobHandlerAuto(String s) {
        XxlJobHelper.log("This is a demo job." + XxlJobHelper.getShardIndex());
        return ReturnT.SUCCESS;
    }

    /**
     * 不自动注册，需要手动到管理中心手动注册的情况
     * @param s
     * @return
     */
    @XxlJob("demo")
    public ReturnT<String> demo(String s) {
        XxlJobHelper.log("This is a demo job." + XxlJobHelper.getShardIndex());
        return ReturnT.SUCCESS;
    }

}
```
### 注解可配置项
|      字段名称      |         默认值          |                        说明                        |
| :--------------------: | :---------------------: | :------------------------------------------------: |
| jobDesc | |任务描述(必填项) |
| author | |负责人(必填项) |
| alarmEmail | |报警邮件，多个以逗号分隔 |
| cron | |cron 表达式(当此项为空时则执行固定频率，如果不为空优先固定频率使用该配置) |
| executorParam | |执行器任务参数 |
| executorFailRetryCount | 0 |失败重试次数 |
| executorTimeout | 0 |执行超时时间 |
| misfireStrategy | DO_NOTHING | 调度过期策略(忽略: DO_NOTHING 立即执行一次: FIRE_ONCE_NOW) |
| executorRouteStrategy | FIRST |执行器路由策略 FIRST:第一个 LAST:最后一个 ROUND:轮询 RANDOM:随机 CONSISTENT_HASH:一致性HASH LEAST_FREQUENTLY_USED:最不经常使用 LEAST_RECENTLY_USED:最近最久未使用 FAILOVER:故障转移 BUSYOVER:忙碌转移 SHARDING_BROADCAST:分片广播 |
| triggerStatus | 0 |调度状态 0 停止 1运行 |
| scheduleConf | 1 |固定速度调用时调用值 如果cron为空时使用此配置 |
| timeUnit | TimeUnit.SECONDS |固定速度时调用速度的单位, 默认为秒 |

### 使用效果

![image-20230619163952025](https://nas.allbs.cn:9006/cloudpic/2023/06/a849936f9b67906bbbe433387ba4536d.png)

![image-20230619164009097](https://nas.allbs.cn:9006/cloudpic/2023/06/7f57e8c810540a79a6323392cf30005f.png)
