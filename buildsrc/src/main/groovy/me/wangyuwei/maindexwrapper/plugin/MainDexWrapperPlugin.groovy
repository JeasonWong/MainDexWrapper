package me.wangyuwei.maindexwrapper.plugin

import com.android.build.gradle.api.ApkVariant
import groovy.io.FileType
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter

import java.util.jar.JarEntry
import java.util.jar.JarFile

import static me.wangyuwei.maindexwrapper.plugin.MainDexWrapperExtension.NAME;

public class MainDexWrapperPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        project.extensions.create(NAME, MainDexWrapperExtension)
        MainDexWrapperExtension extension = project.extensions.getByName(NAME)

        project.afterEvaluate {
            if (project.plugins.findPlugin('com.android.application') && project.android.defaultConfig.multiDexEnabled) {
                project.android.applicationVariants.all { ApkVariant variant ->
                    if (extension.enable) {
                        // hook jarMerging task
                        String jarMergingTaskName = getJarMergingTaskName(variant);
                        def jarMergingTask = project.tasks.findByName(jarMergingTaskName)
                        if (jarMergingTaskName) {
                            jarMergingTask.doLast {
                                jarMergingTask.outputs.files.each {
                                    if (it.isDirectory()) {
                                        new File(it.toString()).eachFileRecurse(FileType.FILES) { File file ->
                                            if (file.toString().endsWith(".jar")) {
                                                JarFile jfile = new JarFile(file.path);
                                                Enumeration files = jfile.entries();
                                                while (files.hasMoreElements()) {
                                                    JarEntry entry = (JarEntry) files.nextElement();
                                                    ClassReader cr = new ClassReader(jfile.getInputStream(entry).bytes)
                                                    ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS)
                                                    ClassVisitor cv = new MainDexWrapperClassVisitor(cw)

                                                    cr.accept(cv, ClassReader.EXPAND_FRAMES)
                                                }
                                                jfile.close()
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        // hook multidex task
                        String multiDexTaskName = "transformClassesWithMultidexlistFor${variant.name.capitalize()}"
                        def multidexTask = project.tasks.findByName(multiDexTaskName)
                        if (multidexTask) {
                            multidexTask.doFirst {
                                def manifestKeepFile = new File("${project.buildDir}/intermediates/multi-dex/${variant.dirName}/manifest_keep.txt")
                                if (extension.keepFile != null) {
                                    extension.keepFile.eachLine { line ->
                                        manifestKeepFile << line + "\n"
                                    }
                                }
                                AnnotationsCollector.get().each { line ->
                                    manifestKeepFile << line + "\n"
                                }
                                AnnotationsCollector.reset()
                            }
                        }
                    }
                }
            }
        }
    }

    private String getJarMergingTaskName(ApkVariant variant) {
        if (variant.buildType.minifyEnabled) {
            return "transformClassesAndResourcesWithProguardFor${variant.name.capitalize()}"
        } else {
            return "transformClassesWithJarMergingFor${variant.name.capitalize()}"
        }
    }
}
