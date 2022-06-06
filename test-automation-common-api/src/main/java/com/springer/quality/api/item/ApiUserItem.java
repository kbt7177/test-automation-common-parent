package com.springer.quality.api.item;

import com.springer.quality.core.environment.Environment;
import com.springer.quality.core.environment.EnvironmentItem;

public class ApiUserItem implements EnvironmentItem {

    private static final String CONFIG_KEY = "api_user";
    private static final ApiUserItem INSTANCE = new ApiUserItem();

    private ApiUserItem(){
        //singleTon
    }

    public static ApiUserItem instance(){
        return INSTANCE;
    }

    @Override
    public <T> T getConfig(Class<T> cls) {
        return Environment.instance().get(CONFIG_KEY, cls);
    }
}
