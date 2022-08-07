package com.trganda.cs;

import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class ClassResourceEnumerator {

    private final ClassLoader classLoader;

    public ClassResourceEnumerator(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public Collection<ClassResource> getAllClasses() throws IOException {
        Collection<ClassResource> classResources = new ArrayList<>();
        for (ClassPath.ClassInfo classInfo : ClassPath.from(classLoader).getAllClasses()) {
            classResources.add(new ClassResourceImp(classLoader, classInfo.getResourceName()));
        }
        return classResources;
    }
}
