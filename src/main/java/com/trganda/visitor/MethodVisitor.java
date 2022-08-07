package com.trganda.visitor;

import com.trganda.data.ClassReference;
import com.trganda.data.FieldReference;
import com.trganda.data.MethodReference;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.util.ArrayList;
import java.util.List;

public class MethodVisitor extends ClassVisitor {
    private String name;
    private String superName;
    private String[] interfaces;
    boolean isInterface;
    private List<FieldReference> members;
    private ClassReference.Handle classHandle;

    private final List<ClassReference> discoveredClasses = new ArrayList<>();
    private final List<MethodReference> discoveredMethods = new ArrayList<>();

    public MethodVisitor() {
        super(Opcodes.ASM8);
    }

    protected MethodVisitor(int api) {
        super(api);
    }

    protected MethodVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.name = name;
        this.superName = superName;
        this.interfaces = interfaces;
        this.isInterface = (access & Opcodes.ACC_INTERFACE) != 0;
        this.members = new ArrayList<>();
        this.classHandle = new ClassReference.Handle(name);

        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        if ((access & Opcodes.ACC_STATIC) == 0) {
            Type type = Type.getType(descriptor);
            String typeName;
            if (type.getSort() == Type.OBJECT || type.getSort() == Type.ARRAY) {
                typeName = type.getInternalName();
            } else {
                typeName = type.getDescriptor();
            }
            members.add(new FieldReference(name, access, new ClassReference.Handle(typeName)));
        }
        return super.visitField(access, name, descriptor, signature, value);
    }

    @Override
    public org.objectweb.asm.MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        boolean isStatic = (access & Opcodes.ACC_STATIC) != 0;
        discoveredMethods.add(new MethodReference(
                classHandle,
                name,
                descriptor,
                isStatic));
        return super.visitMethod(access, name, descriptor, signature, exceptions);
    }

    @Override
    public void visitEnd() {
        ClassReference classReference = new ClassReference(
                name,
                superName,
                interfaces,
                isInterface,
                members.toArray(new FieldReference[0]));
        discoveredClasses.add(classReference);
        super.visitEnd();
    }

    public List<ClassReference> getDiscoveredClasses() {
        return discoveredClasses;
    }

    public List<MethodReference> getDiscoveredMethods() {
        return discoveredMethods;
    }
}
