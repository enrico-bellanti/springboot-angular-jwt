package com.baseApp.backend.seeders;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Slf4j
@Data
public abstract class BaseSeeder<T> implements BaseSeederInterface<T> {
    private Class<T> targetClass;

    private String resourcePath;

    public static void setResourcePath(String path){
        setResourcePath(path);
    }

    public BaseSeeder(Class<T> targetClass, String resourcePath) {
        this.targetClass = targetClass;
        this.resourcePath = resourcePath;
    }

    public void run(){
        List<T> seederList = getJson();
        log.info(this.getClass().getSimpleName() + " started");
        if (!seederList.isEmpty()){
            seed(seederList);
            log.info(this.getClass().getSimpleName() + " completed");
        } else {
            log.info("No seeders to run");
        }
    }

    private List<T> getJson() {
        var resourcePath = getResourcePath() != null ? getResourcePath() : this.resourcePath;

        ResourceLoader resourceLoader = new DefaultResourceLoader();
        var resourceFile = resourceLoader.getResource(resourcePath);

        if (resourceFile.exists()){
            ObjectMapper mapper = new ObjectMapper();
            JavaType type = mapper.getTypeFactory().
                    constructCollectionType(
                            List.class,
                            targetClass);

            try {
                FileInputStream fileInputStream = new FileInputStream(resourceFile.getFile());
                return mapper.readValue(fileInputStream,type);

            } catch (IOException e){
                log.info("Unable to run " + this.getClass().getSimpleName());
            }
        }
        throw new RuntimeException(this.getClass().getSimpleName() + " abort no resource path setted");
    }
}
