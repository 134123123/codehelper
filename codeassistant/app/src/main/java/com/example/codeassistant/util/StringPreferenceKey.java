package com.example.codeassistant.util;

import androidx.datastore.preferences.core.PreferenceKey;
import androidx.datastore.preferences.core.Preferences;

public final class StringPreferenceKey implements PreferenceKey<String> {
    private final String name;

    private StringPreferenceKey(String name) {
        this.name = name;
    }

    public static StringPreferenceKey create(String name) {
        return new StringPreferenceKey(name);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Class<String> getType() {
        return String.class;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringPreferenceKey that = (StringPreferenceKey) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}