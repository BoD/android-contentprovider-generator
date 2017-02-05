package org.jraf.acpg.gradleplugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.ProjectConfigurationException

class AcpgPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        ensurePluginDependencies(project)

        def acgpExtension = project.extensions.create('acgp', AcpgPluginExtension, project)

//        project.task('generateContentProvider') {
//            doLast {
//                println 'Hello, World!'
//            }
//        }

        project.afterEvaluate {
            project.android[variants(project)].all { variant ->
                File sourceFolder = project.file("${project.buildDir}/generated/source/acgp/${variant.dirName}")
                def javaGenerationTask = project.tasks.create(name: "acpgGenerate${variant.name.capitalize()}ContentProvider", type: GenerateContentProvider) {
                    inputDir acgpExtension.inputDir
                    outputDir sourceFolder
                }
                variant.registerJavaGeneratingTask(javaGenerationTask, sourceFolder)
            }
        }
    }

    private static void ensurePluginDependencies(Project project) {
        if (!isApplication(project) && !isLibrary(project)) {
            throw new ProjectConfigurationException("The 'com.android.application' or 'com.android.library' plugin is required.", null)
        }
    }

    private static String variants(Project project) {
        if (isApplication(project)) {
            return 'applicationVariants'
        } else if (isLibrary(project)) {
            return 'libraryVariants'
        }
        throw new ProjectConfigurationException("The 'com.android.application' or 'com.android.library' plugin is required.", null)
    }

    private static boolean isLibrary(Project project) {
        project.plugins.hasPlugin('com.android.library') || project.plugins.hasPlugin('android-library')
    }

    private static boolean isApplication(Project project) {
        project.plugins.hasPlugin('com.android.application') || project.plugins.hasPlugin('android')
    }
}

