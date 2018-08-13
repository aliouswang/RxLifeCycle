package com.example.aliouswang.rxlifecycle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.aliouswang.rxlifecycle.RxLifeCycleActivity;
import com.aliouswang.rxlifecycle.RxLifeCycleEvent;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by aliouswang on 2018/8/13.
 */

public class SecondActivity extends RxLifeCycleActivity{

    private TextView tv_hello;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_http);

        tv_hello = findViewById(R.id.tv_hello);

//        mockHttpRequestWithoutDispose();
//        mockHttp();

        testBindUtil();


    }

    private void mockHttpRequestWithoutDispose() {
        Observable.just("mock http without dispose")
                .delay(10, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> tv_hello.setText(s));
    }

    private void disposeWithRxLifeCycle() {
        bind(Observable.just("mock http without dispose"))
                .delay(10, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> tv_hello.setText(s));
    }

    private void testBindUtil() {
        bindUntil(Observable.just("mock bind until"), RxLifeCycleEvent.ON_DESTORY)
                .delay(10, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> tv_hello.setText(s));
    }

    private Disposable mDisposable;
    private void mockHttp() {
        Observable.just("mock http dispose when onDesotry")
                .delay(10, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(d -> mDisposable = d)
                .subscribe(s -> tv_hello.setText(s));
    }

    @Override
    protected void onDestroy() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        super.onDestroy();
    }
}
