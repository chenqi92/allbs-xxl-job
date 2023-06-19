package cn.allbs.job.enums;

import lombok.Getter;

/**
 * 类 MisfireStrategyEnum
 *
 * @author ChenQi
 * @date 2023/6/19
 */
@Getter
public enum MisfireStrategyEnum {

    /**
     * do nothing
     */
    DO_NOTHING(),

    /**
     * fire once now
     */
    FIRE_ONCE_NOW();


}
