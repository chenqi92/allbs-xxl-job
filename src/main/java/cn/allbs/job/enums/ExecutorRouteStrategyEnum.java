package cn.allbs.job.enums;

/**
 * 枚举 ExecutorRouteStrategyEnum
 *
 * @author ChenQi
 * @date 2023/6/19
 */
public enum ExecutorRouteStrategyEnum {

    FIRST(),
    LAST(),
    ROUND(),
    RANDOM(),
    CONSISTENT_HASH(),
    LEAST_FREQUENTLY_USED(),
    LEAST_RECENTLY_USED(),
    FAILOVER(),
    BUSYOVER(),
    SHARDING_BROADCAST();
}
