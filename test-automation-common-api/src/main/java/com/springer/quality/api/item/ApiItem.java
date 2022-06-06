package com.springer.quality.api.item;

import com.springer.quality.core.environment.Environment;
import com.springer.quality.core.environment.EnvironmentItem;

public class ApiItem implements EnvironmentItem {

    private static final String CONFIG_KEY = "api";
    private static final ApiItem INSTANCE = new ApiItem();

    private ApiItem() {
        //SingleTon
    }

    public static ApiItem instance() {
        return INSTANCE;
    }

    @Override
    public <T> T getConfig(Class<T> cls) {
        return Environment.instance().get(CONFIG_KEY, cls);
    }
}
