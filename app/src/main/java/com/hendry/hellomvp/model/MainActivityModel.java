package com.hendry.hellomvp.model;

import com.hendry.hellomvp.hello.MainActivityHello;

public class MainActivityModel implements MainActivityHello.Model {
    @Override
    public String getData() {
        return "Android MVP ";
    }
}
