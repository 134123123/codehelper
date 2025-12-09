package com.example.codeassistant.util;

import android.content.Context;
import androidx.datastore.preferences.core.MutablePreferences; // 新增这一行
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys; // 关键导入
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;
import io.reactivex.rxjava3.core.Single;

public class DataStoreUtil {
    private static final String DATA_STORE_NAME = "app_preferences";
    private static RxDataStore<Preferences> sDataStore;

    // 使用官方标准写法创建 Key
    private static final Preferences.Key<String> TOKEN_KEY = PreferencesKeys.stringKey("token");

    public static void init(Context context) {
        if (sDataStore == null) {
            sDataStore = new RxPreferenceDataStoreBuilder(context, DATA_STORE_NAME).build();
        }
    }

    // 修改后（正确的写法）：
    public static Single<Preferences> saveToken(String token) {
        return sDataStore.updateDataAsync(prefs -> {
            // 1. 转换为可变对象
            MutablePreferences mutablePreferences = prefs.toMutablePreferences();
            // 2. 设置值
            mutablePreferences.set(TOKEN_KEY, token);
            // 3. 返回修改后的对象（包装在 Single 中）
            return Single.just(mutablePreferences);
        });
    }

    public static Single<String> getToken() {
        return sDataStore.data().firstOrError()
                .map(prefs -> prefs.get(TOKEN_KEY) != null ? prefs.get(TOKEN_KEY) : "");
    }
}