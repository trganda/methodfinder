package com.trganda.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Util {
    private static final Logger LOGGER = LoggerFactory.getLogger(Util.class);

    public static ClassLoader getJarClassLoader(List<URL> classPathUrls) throws MalformedURLException {
        return new URLClassLoader(classPathUrls.toArray(new URL[0]));
    }
}
