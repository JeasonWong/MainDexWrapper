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
            if (project.plugins.findPlugin('com.android.application') && project.android.defaultConfig.multiDexEnabled) {
                project.android.applicationVariants.all { ApkVariant variant ->
                    if (extension.enable && extension.keepFile != null) {
                        String taskName = "transformClassesWithMultidexlistFor${variant.name.capitalize()}"
                        def multidexTask = project.tasks.findByName(taskName)
                        if (multidexTask) {
                            StringBuilder sb = new StringBuilder()
                            extension.keepFile.eachLine { line ->
                                sb.append(line).append("\n")
                            }
                            multidexTask.doFirst {
                                def manifestKeepFile = new File("${project.buildDir}/intermediates/multi-dex/${variant.dirName}/manifest_keep.txt")
                                manifestKeepFile << sb.toString()
                            }
                        }
                    }
                }
            }
        }
    }
}
