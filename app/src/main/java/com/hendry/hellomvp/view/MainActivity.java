package com.hendry.hellomvp.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hendry.hellomvp.R;
import com.hendry.hellomvp.hello.MainActivityHello;
import com.hendry.hellomvp.presenter.MainActivityPresenter;
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
