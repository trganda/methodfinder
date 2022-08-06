package com.trganda.visitor;

import com.trganda.data.ClassReference;
import org.objectweb.asm.ClassVisitor;

import java.util.List;

public class MethodDiscoveryVisitor extends ClassVisitor {
    private String name;
    private String superName;
    private String[] interfaces;
    boolean isInterface;
    private List<ClassReference.Member> members;
    private ClassReference.Handle classHandle;

    protected MethodDiscoveryVisitor(int api) {
        super(api);
    }

    protected MethodDiscoveryVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }
}
