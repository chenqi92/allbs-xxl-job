package cn.allbs.job.enums;

import lombok.Getter;

/**
 * 枚举 ExecutorRouteStrategyEnum
 *
 * @author ChenQi
 * @date 2023/6/19
 */
@Getter
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
