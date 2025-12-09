// 包路径：com.example.codeassistant.util
package com.example.codeassistant.util;

import android.content.Context;

import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.StringPreferenceKey; // 关键导入：添加StringPreferenceKey的导入
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;

import io.reactivex.rxjava3.core.Single;

public class DataStoreUtil {
    private static final String DATA_STORE_NAME = "app_preferences";
    // 修复：确保StringPreferenceKey被正确导入，否则会爆红
    private static final StringPreferenceKey TOKEN_KEY = StringPreferenceKey.create("token");
    private static RxDataStore<Preferences> sDataStore;

    // 初始化DataStore（在Application中调用）
    public static void init(Context context) {
        if (sDataStore == null) {
            sDataStore = new RxPreferenceDataStoreBuilder(context, DATA_STORE_NAME).build();
        }
    }

    // 保存Token
    public static Single<Preferences> saveToken(String token) {
        return sDataStore.updateDataAsync(prefs -> {
            Preferences.Builder builder = prefs.toBuilder();
            builder.set(TOKEN_KEY, token);
            return Single.just(builder.build());
        });
    }

    // 获取Token（返回Single，需在后台线程订阅）
    public static Single<String> getToken() {
        return sDataStore.data().firstOrError()
                .map(prefs -> prefs.get(TOKEN_KEY) != null ? prefs.get(TOKEN_KEY) : "");
    }
}