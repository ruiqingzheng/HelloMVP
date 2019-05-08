// 全局接口类, 定义接口规定model, presenter, view 中分别需要实现的方法

package com.hendry.hellomvp.hello;

public interface MainActivityHello {
    // 定义activity 中需要实现的方法 ,
    // 1. 一般也就是activity 常规的初始化控件
    // 2. 更新ui的方法
    interface View {
        void initView();
        void setViewData(String data);
    }

    // model 比较简单, 只需要实现获取数据即可
    interface Model {
        String getData();
    }

    // presenter 中会定义内部变量来访问activity 和 model
    // presenter 中需要定义事件处理方法, 来响应view的事件委托
    interface Presenter {
        void onClick(android.view.View view);
    }
}