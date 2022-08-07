package com.trganda.data;

public class FieldReference {
    private final String name;
    private final int modifiers;
    private final ClassReference.Handle type;

    public FieldReference(String name, int modifiers, ClassReference.Handle type) {
        this.name = name;
        this.modifiers = modifiers;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getModifiers() {
        return modifiers;
    }

    public ClassReference.Handle getType() {
        return type;
    }
}
