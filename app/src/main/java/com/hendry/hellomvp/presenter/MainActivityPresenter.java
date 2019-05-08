package com.hendry.hellomvp.presenter;

import android.view.View;

import com.hendry.hellomvp.hello.MainActivityHello;
import com.hendry.hellomvp.model.MainActivityModel;
import com.hendry.hellomvp.view.MainActivity;

public class MainActivityPresenter implements MainActivityHello.Presenter {

    // presenter中介类 既能访问activity，又能访问model，
    // 于是需要定义activity对应的内部变量和model对应的内部变量
    MainActivity mView ;
    MainActivityModel mModel ;

    public MainActivityPresenter(MainActivity view) {
        // presenter 的构造方法中，常规需要完成的有：
        // 1. 调用View的初始化控件， 这样在view中的生命周期方法中初始化presenter实例的时候就会调用该方法， 这样就和我们没有使用mvp模式是类似的或者说一致
        // 2. 实例化model
        mView = view;
        mView.initView();
        mModel = new MainActivityModel();
    }

    // 同时presenter 也要处理事件委托
    @Override
    public void onClick(View view) {

        String data = mModel.getData();
        mView.setViewData(data);
    }
}
