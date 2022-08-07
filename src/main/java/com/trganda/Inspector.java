package com.trganda;

import com.trganda.cs.ClassResourceEnumerator;
import com.trganda.data.MethodReference;
import com.trganda.util.MethodDiscovery;
import com.trganda.util.Util;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;
import org.objectweb.asm.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Inspector {

    private static final Logger LOGGER = LoggerFactory.getLogger(Inspector.class);

    private static void printUsage() {
        System.out.println("Usage:\n    Pass the path of a library, which contain jar, war, or class file.");
    }

    public static void main(String[] args) throws IOException {

        if (args.length == 0) {
            printUsage();
            System.exit(1);
        }

        configureLogging();

        Path[] libPaths = new Path[args.length];
        for (int i = 0; i < args.length; i++) {
            Path path = Paths.get(args[i]).toAbsolutePath();
            if (!Files.exists(path)) {
                throw new IllegalArgumentException("Invalid path " + path);
            }
            libPaths[i] = path;
        }
        LOGGER.info("Using classpath: " + Arrays.toString(libPaths));

        List<URL> parsedLibPaths = new ArrayList<>();
        visitAllDir(libPaths[0].toFile(), parsedLibPaths);

        ClassLoader classLoader = Util.getJarClassLoader(parsedLibPaths);
        ClassResourceEnumerator classResourceEnumerator = new ClassResourceEnumerator(classLoader);

        MethodDiscovery methodDiscovery = new MethodDiscovery();
        methodDiscovery.discover(classResourceEnumerator);

        for (MethodReference mref : methodDiscovery.getDiscoveredMethods()) {
            String desc = mref.getDesc();
            Type[] types = Type.getArgumentTypes(desc);
            if (types.length == 2 &&
                    types[0].getInternalName().endsWith("HttpServletRequest") &&
                    types[1].getInternalName().endsWith("HttpServletResponse")) {
                LOGGER.info("[+]: Found Servlet " + mref.getClassReference().getName() + "." + mref.getName() + "()");
            }
        }

    }

    private static void configureLogging() {
        ConsoleAppender console = new ConsoleAppender();
        String PATTERN = "%d %c [%p] %m%n";
        console.setLayout(new PatternLayout(PATTERN));
        console.setThreshold(Level.DEBUG);
        console.activateOptions();
        org.apache.log4j.Logger.getRootLogger().addAppender(console);
    }

    private static void visitAllDir(File dir, List<URL> ret) throws MalformedURLException {
        if (dir.isDirectory()) {
            for (File childDir : Objects.requireNonNull(dir.listFiles())) {
                visitAllDir(childDir, ret);
            }
        } else if (dir.getName().endsWith(".jar") || dir.getName().endsWith(".class")) {
            ret.add(dir.getAbsoluteFile().toURI().toURL());
        }
    }
}
