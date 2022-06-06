package com.springer.quality.core.environment;

public interface EnvironmentItem {
    <T> T getConfig(Class<T> cls);
}
