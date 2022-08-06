package com.trganda.data;


public class ClassReference {
    private final String name;
    private final String superClass;
    private final String[] interfaces;
    private final boolean isInterface;
    private final Member[] members;

    public ClassReference(String name, String superClass, String[] interfaces, boolean isInterface, Member[] members) {
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

    public Member[] getMembers() {
        return members;
    }

    public static class Member {
        private final String name;
        private final int modifiers;
        private final ClassReference.Handle type;

        public Member(String name, int modifiers, Handle type) {
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

        public Handle getType() {
            return type;
        }
    }

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
