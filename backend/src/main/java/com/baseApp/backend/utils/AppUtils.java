package com.baseApp.backend.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public final class AppUtils {
    public static Properties loadProperties(String resourceFileName)  {

        try {
            Properties configuration = new Properties();
            InputStream inputStream = AppUtils.class
                    .getClassLoader()
                    .getResourceAsStream(resourceFileName);
            configuration.load(inputStream);
            inputStream.close();
            return configuration;
        } catch (IOException e){
            log.error(e.getMessage());
        }
        return null;
    }

    public static Properties loadAppProperties(){
        return loadProperties("application.properties");
    }
}