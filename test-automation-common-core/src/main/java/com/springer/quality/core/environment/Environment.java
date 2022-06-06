package com.springer.quality.core.environment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

@Slf4j
public class Environment {
    public static final String ENV_PROPERTY = System.getProperty("env", "local");
    private static final String ENV_FILE = "common-" + ENV_PROPERTY + ".yaml";
    private static final Environment ENVIRONMENT = new Environment();
    private Map<String, Object> properties;


    private Environment() {
        URL url = Environment.class.getClassLoader().getResource(ENV_FILE);
        if (url == null) {
            log.warn("Configuration file not found in resource : {}", ENV_FILE);
            return;
        }
        try {
            File envFile = new File(url.toURI());
            log.info("Setting Configuration from file : " + envFile);
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
            properties = mapper.readValue(envFile, Map.class);
        } catch (URISyntaxException | IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public <T> T get(String key, Class<T> cls) {
        if (properties == null) {
            throw new IllegalStateException("No configuration file found in resource, Add a common-{env}.yaml file to resource");
        }
        Object obj = properties.get(key);
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        return mapper.convertValue(obj, cls);
    }

    public static Environment instance() {
        return ENVIRONMENT;
    }

}
