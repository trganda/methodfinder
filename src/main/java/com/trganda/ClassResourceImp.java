package com.trganda;

import java.io.IOException;
import java.io.InputStream;

public class ClassResourceImp implements ClassResource{

    private final ClassLoader classLoader;
    private final String resourceName;

    public ClassResourceImp(ClassLoader classLoader, String resourceName) {
        this.classLoader = classLoader;
        this.resourceName = resourceName;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return classLoader.getResourceAsStream(resourceName);
    }

    @Override
    public String getName() {
        return resourceName;
    }
}
