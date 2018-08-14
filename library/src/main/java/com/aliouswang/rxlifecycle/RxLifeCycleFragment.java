package com.aliouswang.rxlifecycle;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by aliouswang on 2018/8/14.
 */

public class RxLifeCycleFragment extends Fragment{

    private BehaviorSubject<RxLifeCycleEvent> mDisposeSubject = BehaviorSubject.create();

    public BehaviorSubject<RxLifeCycleEvent> getDisposeSubject() {
        return mDisposeSubject;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDisposeSubject.onNext(RxLifeCycleEvent.ON_CREATE);
    }

    @Override
    public void onStart() {
        super.onStart();
        mDisposeSubject.onNext(RxLifeCycleEvent.ON_START);
    }

    @Override
    public void onResume() {
        super.onResume();
        mDisposeSubject.onNext(RxLifeCycleEvent.ON_RESUME);
    }

    @Override
    public void onPause() {
        super.onPause();
        mDisposeSubject.onNext(RxLifeCycleEvent.ON_PAUSE);
    }

    @Override
    public void onStop() {
        super.onStop();
        mDisposeSubject.onNext(RxLifeCycleEvent.ON_STOP);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDisposeSubject.onNext(RxLifeCycleEvent.ON_DESTORY);
    }
}
