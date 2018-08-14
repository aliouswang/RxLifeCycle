package com.aliouswang.rxlifecycle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by aliouswang on 2018/8/13.
 */

public class RxLifeCycleActivity extends AppCompatActivity{

    private BehaviorSubject<RxLifeCycleEvent> mDisposeSubject = BehaviorSubject.create();

    protected <T> Observable<T> bind(Observable<T> observable) {
        return observable.compose(upstream ->
                observable.takeUntil(mDisposeSubject));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDisposeSubject.onNext(RxLifeCycleEvent.ON_CREATE);
    }



    private Disposable disposable;

    protected <T> Observable<T> bindUntil(Observable<T> observable, RxLifeCycleEvent event) {
        return observable.compose(upstream ->
                observable.takeUntil(mDisposeSubject.filter(
                        rxLifeCycleEvent -> rxLifeCycleEvent.getValue() >= event.getValue())));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDisposeSubject.onNext(RxLifeCycleEvent.ON_START);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDisposeSubject.onNext(RxLifeCycleEvent.ON_RESUME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDisposeSubject.onNext(RxLifeCycleEvent.ON_PAUSE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mDisposeSubject.onNext(RxLifeCycleEvent.ON_STOP);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisposeSubject.onNext(RxLifeCycleEvent.ON_DESTORY);

        if (disposable != null) {
            if (!disposable.isDisposed()) {
                disposable.dispose();
            }
        }
    }
}
