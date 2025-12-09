// 包路径：com.example.codeassistant
package com.example.codeassistant;

import android.app.Application;

import com.example.codeassistant.util.DataStoreUtil;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化DataStore
        DataStoreUtil.init(this);
    }
}