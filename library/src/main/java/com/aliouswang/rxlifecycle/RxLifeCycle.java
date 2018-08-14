package com.aliouswang.rxlifecycle;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.support.annotation.RequiresApi;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by aliouswang on 2018/8/14.
 */

public class RxLifeCycle {

    private static final String LIFECYCLE_FRAGMNET_TAG = "LIFECYCLE_FRAGMENT_TAG";
    private BehaviorSubject<RxLifeCycleEvent> mBehaviorSubject;

    public RxLifeCycle(BehaviorSubject<RxLifeCycleEvent> behaviorSubject) {
        this.mBehaviorSubject = behaviorSubject;
    }

    public BehaviorSubject<RxLifeCycleEvent> getBehaviorSubject() {
        return mBehaviorSubject;
    }

    public static RxLifeCycle bind(Activity activity) {
        FragmentManager fragmentManager = activity.getFragmentManager();
        RxLifeCycleFragment lifeCycleFragment = (RxLifeCycleFragment)
                fragmentManager.findFragmentByTag(LIFECYCLE_FRAGMNET_TAG);
        if (lifeCycleFragment == null) {
            lifeCycleFragment = new RxLifeCycleFragment();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(lifeCycleFragment, LIFECYCLE_FRAGMNET_TAG);
            transaction.commit();
        }else if (lifeCycleFragment.isDetached()) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.attach(lifeCycleFragment);
            transaction.commit();
        }
        return new RxLifeCycle(lifeCycleFragment.getDisposeSubject());
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static RxLifeCycle bind(Fragment fragment) {
        FragmentManager fragmentManager = fragment.getChildFragmentManager();
        RxLifeCycleFragment lifeCycleFragment = (RxLifeCycleFragment)
                fragmentManager.findFragmentByTag(LIFECYCLE_FRAGMNET_TAG);
        if (lifeCycleFragment == null) {
            lifeCycleFragment = new RxLifeCycleFragment();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(lifeCycleFragment, LIFECYCLE_FRAGMNET_TAG);
            transaction.commit();
        }else if (lifeCycleFragment.isDetached()) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.attach(lifeCycleFragment);
            transaction.commit();
        }
        return new RxLifeCycle(lifeCycleFragment.getDisposeSubject());
    }

    public static <T> ObservableTransformer<T, T> disposeUtil(Observable<RxLifeCycleEvent> subject,
                                                           RxLifeCycleEvent event) {
        return upstream -> upstream.takeUntil(
                subject.filter(rxLifeCycleEvent -> rxLifeCycleEvent.getValue() >= event.getValue()));
    }

    public static <T> ObservableTransformer<T, T> bindUtil(Activity activity, RxLifeCycleEvent event) {
        RxLifeCycle rxLifeCycle = bind(activity);
        return rxLifeCycle.disposeUtil(rxLifeCycle.getBehaviorSubject(), event);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static <T> ObservableTransformer<T, T> bindUtil(Fragment fragment, RxLifeCycleEvent event) {
        RxLifeCycle rxLifeCycle = bind(fragment);
        return rxLifeCycle.disposeUtil(rxLifeCycle.getBehaviorSubject(), event);
    }

}
