package com.trganda.util;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Util {
    private static final Logger LOGGER = LoggerFactory.getLogger(Util.class);

    public static ClassLoader getJarClassLoader(Path ... jarPaths) throws MalformedURLException {
        final List<URL> classPathUrls = new ArrayList<>(jarPaths.length);
        for (Path jarPath : jarPaths) {
            if (!Files.exists(jarPath) || Files.isDirectory(jarPath)) {
                throw new IllegalArgumentException("Path \"" + jarPath + "\" is not a path to a file.");
            }
            classPathUrls.add(jarPath.toUri().toURL());
        }
        URLClassLoader classLoader = new URLClassLoader(classPathUrls.toArray(new URL[classPathUrls.size()]));
        return classLoader;
    }
}
