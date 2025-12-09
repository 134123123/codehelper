package com.example.codeassistant.util;

import androidx.datastore.preferences.core.Preferences;

public interface PreferenceKey<T> {
    String getName();
    Class<T> getType();
}