package cn.allbs.job.properties;

/**
 * 执行器配置
 *
 * @author ChenQi
 */
public class XxlExecutorProperties {

    /**
     * 执行器AppName [选填]：执行器心跳注册分组依据；为空则关闭自动注册
     */
    private String appname;

    /**
     * 服务注册地址,优先使用该配置作为注册地址 为空时使用内嵌服务 ”IP:PORT“ 作为注册地址 从而更灵活的支持容器类型执行器动态IP和动态映射端口问题
     */
    private String address;

    /**
     * 执行器IP [选填]：默认为空表示自动获取IP，多网卡时可手动设置指定IP ，该IP不会绑定Host仅作为通讯实用；地址信息用于 "执行器注册" 和
     * "调度中心请求并触发任务"
     */
    private String ip;

    /**
     * 执行器端口号 [选填]：小于等于0则自动获取；默认端口为9999，单机部署多个执行器时，注意要配置不同执行器端口；
     */
    private Integer port = 0;

    /**
     * 执行器通讯TOKEN [选填]：非空时启用；
     */
    private String accessToken;

    /**
     * 执行器运行日志文件存储磁盘路径 [选填] ：需要对该路径拥有读写权限；为空则使用默认路径；
     */
    private String logPath = "logs/applogs/xxl-job/jobhandler";

    /**
     * 执行器日志保存天数 [选填] ：值大于3时生效，启用执行器Log文件定期清理功能，否则不生效；
     */
    private Integer logRetentionDays = 30;

    /**
     * 是否自动注册(为0则自动注册)
     */
    private Integer autoRegister = 0;

    /**
     * 手动注册地址列表（以逗号分割）
     */
    private String manualAddressList;

    public XxlExecutorProperties() {
    }

    public String getAppname() {
        return this.appname;
    }

    public String getAddress() {
        return this.address;
    }

    public String getIp() {
        return this.ip;
    }

    public Integer getPort() {
        return this.port;
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public String getLogPath() {
        return this.logPath;
    }

    public Integer getLogRetentionDays() {
        return this.logRetentionDays;
    }

    public Integer getAutoRegister() {
        return this.autoRegister;
    }

    public String getManualAddressList() {
        return this.manualAddressList;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }

    public void setLogRetentionDays(Integer logRetentionDays) {
        this.logRetentionDays = logRetentionDays;
    }

    public void setAutoRegister(Integer autoRegister) {
        this.autoRegister = autoRegister;
    }

    public void setManualAddressList(String manualAddressList) {
        this.manualAddressList = manualAddressList;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof XxlExecutorProperties)) return false;
        final XxlExecutorProperties other = (XxlExecutorProperties) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$appname = this.getAppname();
        final Object other$appname = other.getAppname();
        if (this$appname == null ? other$appname != null : !this$appname.equals(other$appname)) return false;
        final Object this$address = this.getAddress();
        final Object other$address = other.getAddress();
        if (this$address == null ? other$address != null : !this$address.equals(other$address)) return false;
        final Object this$ip = this.getIp();
        final Object other$ip = other.getIp();
        if (this$ip == null ? other$ip != null : !this$ip.equals(other$ip)) return false;
        final Object this$port = this.getPort();
        final Object other$port = other.getPort();
        if (this$port == null ? other$port != null : !this$port.equals(other$port)) return false;
        final Object this$accessToken = this.getAccessToken();
        final Object other$accessToken = other.getAccessToken();
        if (this$accessToken == null ? other$accessToken != null : !this$accessToken.equals(other$accessToken))
            return false;
        final Object this$logPath = this.getLogPath();
        final Object other$logPath = other.getLogPath();
        if (this$logPath == null ? other$logPath != null : !this$logPath.equals(other$logPath)) return false;
        final Object this$logRetentionDays = this.getLogRetentionDays();
        final Object other$logRetentionDays = other.getLogRetentionDays();
        if (this$logRetentionDays == null ? other$logRetentionDays != null : !this$logRetentionDays.equals(other$logRetentionDays))
            return false;
        final Object this$autoRegister = this.getAutoRegister();
        final Object other$autoRegister = other.getAutoRegister();
        if (this$autoRegister == null ? other$autoRegister != null : !this$autoRegister.equals(other$autoRegister))
            return false;
        final Object this$manualAddressList = this.getManualAddressList();
        final Object other$manualAddressList = other.getManualAddressList();
        if (this$manualAddressList == null ? other$manualAddressList != null : !this$manualAddressList.equals(other$manualAddressList))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof XxlExecutorProperties;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $appname = this.getAppname();
        result = result * PRIME + ($appname == null ? 43 : $appname.hashCode());
        final Object $address = this.getAddress();
        result = result * PRIME + ($address == null ? 43 : $address.hashCode());
        final Object $ip = this.getIp();
        result = result * PRIME + ($ip == null ? 43 : $ip.hashCode());
        final Object $port = this.getPort();
        result = result * PRIME + ($port == null ? 43 : $port.hashCode());
        final Object $accessToken = this.getAccessToken();
        result = result * PRIME + ($accessToken == null ? 43 : $accessToken.hashCode());
        final Object $logPath = this.getLogPath();
        result = result * PRIME + ($logPath == null ? 43 : $logPath.hashCode());
        final Object $logRetentionDays = this.getLogRetentionDays();
        result = result * PRIME + ($logRetentionDays == null ? 43 : $logRetentionDays.hashCode());
        final Object $autoRegister = this.getAutoRegister();
        result = result * PRIME + ($autoRegister == null ? 43 : $autoRegister.hashCode());
        final Object $manualAddressList = this.getManualAddressList();
        result = result * PRIME + ($manualAddressList == null ? 43 : $manualAddressList.hashCode());
        return result;
    }

    public String toString() {
        return "XxlExecutorProperties(appname=" + this.getAppname() + ", address=" + this.getAddress() + ", ip=" + this.getIp() + ", port=" + this.getPort() + ", accessToken=" + this.getAccessToken() + ", logPath=" + this.getLogPath() + ", logRetentionDays=" + this.getLogRetentionDays() + ", autoRegister=" + this.getAutoRegister() + ", manualAddressList=" + this.getManualAddressList() + ")";
    }
}
