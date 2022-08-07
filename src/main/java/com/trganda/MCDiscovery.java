package com.trganda;

import com.trganda.data.ClassReference;
import com.trganda.data.MethodReference;
import com.trganda.visitor.MethodVisitor;
import org.objectweb.asm.ClassReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MCDiscovery {

    private static final Logger LOGGER = LoggerFactory.getLogger(MCDiscovery.class);
    private final List<ClassReference> discoveredClasses = new ArrayList<>();
    private final List<MethodReference> discoveredMethods = new ArrayList<>();

    public void discover(ClassResourceEnumerator classResourceEnumerator) throws IOException {
        for (ClassResource classResource : classResourceEnumerator.getAllClasses()) {
            try (InputStream in = classResource.getInputStream()) {
                ClassReader cr = new ClassReader(in);
                try {
                    MethodVisitor methodVisitor = new MethodVisitor();
                    cr.accept(methodVisitor, ClassReader.EXPAND_FRAMES & ClassReader.SKIP_DEBUG);
                    discoveredClasses.addAll(methodVisitor.getDiscoveredClasses());
                    discoveredMethods.addAll(methodVisitor.getDiscoveredMethods());
                } catch (Exception e) {
                    LOGGER.error("Exception analyzing: " + classResource.getName(), e);
                }
            }
        }
    }

    public void save() {

    }

    public List<ClassReference> getDiscoveredClasses() {
        return discoveredClasses;
    }

    public List<MethodReference> getDiscoveredMethods() {
        return discoveredMethods;
    }
}
