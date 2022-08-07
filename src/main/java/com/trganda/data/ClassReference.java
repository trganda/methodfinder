package com.trganda.data;

public class ClassReference {
    private final String name;
    private final String superClass;
    private final String[] interfaces;
    private final boolean isInterface;
    private final FieldReference[] members;

    public ClassReference(String name, String superClass, String[] interfaces, boolean isInterface, FieldReference[] members) {
        this.name = name;
        this.superClass = superClass;
        this.interfaces = interfaces;
        this.isInterface = isInterface;
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public String getSuperClass() {
        return superClass;
    }

    public String[] getInterfaces() {
        return interfaces;
    }

    public boolean getIsInterface() {
        return isInterface;
    }

    public FieldReference[] getMembers() {
        return members;
    }

    /**
     * Reference to a Class with qualified class name.
     */
    public static class Handle {
        private final String name;

        public Handle(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
