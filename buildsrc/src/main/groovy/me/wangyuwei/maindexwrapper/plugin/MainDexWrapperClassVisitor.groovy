package me.wangyuwei.maindexwrapper.plugin

import me.wangyuwei.maindexwrapper.annotations.KeepMainDex
import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type

public class MainDexWrapperClassVisitor extends ClassVisitor {

    private String className;

    MainDexWrapperClassVisitor(ClassVisitor classVisitor) {
        super(Opcodes.ASM5, classVisitor)
    }

    @Override
    AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        if (Type.getDescriptor(KeepMainDex.class) == desc) {
            AnnotationsCollector.put(generateKeepRules())
        }
        return super.visitAnnotation(desc, visible)
    }

    @Override
    void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces)
        className = name;
    }

    private String generateKeepRules() {
        if (className == null) {
            return ""
        }
        return "-keep class ${className.replaceAll("/", ".")} {*;}"
    }

}