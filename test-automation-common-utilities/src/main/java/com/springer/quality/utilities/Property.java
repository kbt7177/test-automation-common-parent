package com.springer.quality.utilities;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Slf4j
public class Property {

    private static Properties prop = null;
    private static final String ENV_PROPERTY = System.getProperty("env", "local");

    private static Properties loadPropertiesFiles() {
        String userDir = System.getProperty("user.dir");
        String resourcesFolder = "/src/main/resources/";
        String filePath = null;
        if (prop != null) {
            return prop;
        } else {
            try {
                prop = new Properties();
                filePath = userDir + resourcesFolder + ENV_PROPERTY + "/";
                loadFiles(filePath);
                log.info("All properties files are loaded from location : " + resourcesFolder + ENV_PROPERTY);
            } catch (Exception e) {
                log.error("Error in loading properties file from location : " + resourcesFolder + ENV_PROPERTY, e);
            }
        }
        return prop;
    }

    private static void loadFiles(String filePath) throws IOException {
        File fileList[] = new File(filePath).listFiles();
        String fileName = "";
        for (File file : fileList) {
            if (file.isDirectory()) {
                loadFiles(file.getAbsolutePath());
            } else {
                fileName = file.getAbsolutePath();
                if (fileName.endsWith(".properties")) {
                    try {
                        FileInputStream fis = new FileInputStream(fileName);
                        log.debug("Properties file loaded  : " + file.getName());
                        prop.load(fis);
                    } catch (IOException e) {
                        log.error("Exception in loading file : " + file.getName());
                    }
                }
            }
        }
    }

    public static String get(String propertyName) {
        return loadPropertiesFiles().getProperty(propertyName);
    }

}
