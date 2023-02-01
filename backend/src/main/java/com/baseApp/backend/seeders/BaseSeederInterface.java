package com.baseApp.backend.seeders;

import org.springframework.core.io.Resource;

import java.util.List;

public interface BaseSeederInterface<T> {
    public void seed(List<T> list);
}
