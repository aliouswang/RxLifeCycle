package com.aliouswang.rxlifecycle;

/**
 * Created by aliouswang on 2018/8/13.
 */

public enum RxLifeCycleEvent {

    ON_CREATE(0),
    ON_START(1),
    ON_RESUME(2),
    ON_PAUSE(3),
    ON_STOP(4),
    ON_DESTORY(5);

    private final int value;

    RxLifeCycleEvent(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
