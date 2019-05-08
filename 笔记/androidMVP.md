# android MVP

最基本的 MVP 例子来理解该设计模式

参考视频
`https://www.youtube.com/watch?v=9hw0SRtOZ9wxs`

[源码](https://github.com/ruiqingzheng/HelloMVP.git)

## 基本的 MVP 结构项目

新建一个全新项目,在项目下新建 4 个 package , 其中 3 个分别对应的是 model , presenter , view

另外一个是将这 3 个结合起来的全局接口, 比如这里叫 hello

### 全局接口类中需要定义至少 3 个 interface

每个接口分别对应 view 类, model 类和 presenter 类,

规定这些类中需要实现的方法, 这样规划的思想是:

#### **_model 接口_**

-- 负责获取数据, 比如从数据库, 文件, 网络获取

而且 model 不会和 view 进行交互, 一般只是 presenter 会调用 model 来获取数据

**_所以该接口中需要定义获取数据的方法_**

---

#### **view 接口**

-- 常规理解就是 Activity, Fragment
和一般的 Activity 不同的是, 虽然 view 中依然要定义初始化控件方法(比如 button, textview 等)和更新 ui 方法(比如 setTextView),
但这些方法并不是直接调用,而是委托给 presenter 调用, 因为 presenter 中能访问 model 数据

**_该接口中必须定义初始化 view 中的子控件方法, 还有更新 ui 的方法_**

---

#### **presenter 接口**

-- 类似中介, presenter 中既能访问 view, 也能访问 model
同时要实现事件处理方法, 因为在 View(activity)中, 不直接对事件进行处理(view 中没有直接接触数据),
但是 view 是直接和用户交互的, 于是当 view 事件被触发时候, 就去调用 presenter 中的方法来处理事件,
也就是将事件委托给 presenter, 也就是调用 presenter 中的事件处理方法
类似在前端中也有这样的设计模式, 在组件化以后把事件委托给父组件处理, 一般也是因为在父组件中更方便获取到数据

**_该接口中必须定义的方法, 就是事件委托的处理方法_**

---

## 全局接口类定义 MVP 对应接口

// com.hendry.hellomvp.hello.MainActivityHello

```java

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
```

## Modle 类的实现

// com.hendry.hellomvp.model.MainActivityModel

```java
package com.hendry.hellomvp.model;

import com.hendry.hellomvp.hello.MainActivityHello;

public class MainActivityModel implements MainActivityHello.Model {
    @Override
    public String getData() {
        return "Android MVP ";
    }
}
```

## Presenter 类的实现

// com.hendry.hellomvp.presenter.MainActivityPresenter

```java
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


```

## View 类的实现

```java

public class MainActivity extends AppCompatActivity implements MainActivityHello.View {

    Button mBtn;
    TextView mTv;
    MainActivityPresenter mainActivityPresenter;

    // view的生命周期中实例化presenter ， 而presenter构造函数中将调用view的初始化view控件， 还有实例化model
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivityPresenter = new MainActivityPresenter(this);
    }

    // 初始化view内的控件， 但该方法由presenter来调用

    @Override
    public void initView() {
        mTv = findViewById(R.id.tv);
        mBtn = findViewById(R.id.button);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivityPresenter.onClick(v);
            }
        });
    }

    // view方法中定义更新ui的方法， 但是该方法同样由presenter来调用

    @Override
    public void setViewData(String data) {
        mTv.setText(data);
    }
}

```
