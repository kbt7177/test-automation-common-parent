package com.springer.quality.database.item;

import com.springer.quality.core.environment.Environment;
import com.springer.quality.core.environment.EnvironmentItem;

public class DatabaseItem implements EnvironmentItem {

    private static final String DATABASE_CONFIG_KEY = "database";
    private static final DatabaseItem INSTANCE = new DatabaseItem();

    private DatabaseItem() {
        //SingleTon
    }

    public static DatabaseItem instance() {
        return INSTANCE;
    }

    @Override
    public <T> T getConfig(Class<T> cls) {
        return Environment.instance().get(DATABASE_CONFIG_KEY, cls);
    }
}
