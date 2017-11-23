package me.wangyuwei.maindexwrapper.plugin

import com.android.build.gradle.api.ApkVariant
import org.gradle.api.Plugin
import org.gradle.api.Project

import static me.wangyuwei.maindexwrapper.plugin.MainDexWrapperExtension.NAME;

public class MainDexWrapperPlugin implements Plugin<Project> {

    private MainDexWrapperExtension extension

    @Override
    void apply(Project project) {

        project.extensions.create(NAME, MainDexWrapperExtension)
        extension = project.extensions.getByName(NAME)

        project.afterEvaluate {
            if (project.plugins.findPlugin('com.android.application')) {
                project.android.applicationVariants.all { ApkVariant variant ->
                    println extension.toString()
                }
            }
        }
    }
}
