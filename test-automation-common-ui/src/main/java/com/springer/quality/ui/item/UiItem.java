package com.springer.quality.ui.item;

import com.springer.quality.core.environment.Environment;
import com.springer.quality.core.environment.EnvironmentItem;

public class UiItem implements EnvironmentItem {
    private static final String CONFIG_KEY = "ui";
    private static final UiItem INSTANCE = new UiItem();

    private UiItem() {
        //SingleTon
    }

    public static UiItem instance() {
        return INSTANCE;
    }

    @Override
    public <T> T getConfig(Class<T> cls) {
        return Environment.instance().get(CONFIG_KEY, cls);
    }
}
