/*
 * This source is part of the
 *      _____  ___   ____
 *  __ / / _ \/ _ | / __/___  _______ _
 * / // / , _/ __ |/ _/_/ _ \/ __/ _ `/
 * \___/_/|_/_/ |_/_/ (_)___/_/  \_, /
 *                              /___/
 * repository.
 *
 * Copyright (C) 2012-2017 Benoit 'BoD' Lubek (BoD@JRAF.org)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.jraf.acpg.gradleplugin

import com.android.builder.core.DefaultManifestParser
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.ProjectConfigurationException

class AcpgPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        ensurePluginDependencies(project)

        def acpgExtension = project.extensions.create('acpg', AcpgPluginExtension, project)

        project.afterEvaluate {
            // Get the packageName from the manifest
            def manifestParser = new DefaultManifestParser(project.android.sourceSets.main.manifest.srcFile)
            def packageName = manifestParser.getPackage()

            project.android[variants(project)].all { variant ->
                File sourceFolder = project.file("${project.buildDir}/generated/source/acpg/${variant.dirName}")
                acpgExtension.packageName packageName
                def javaGenerationTask = project.tasks.create(name: "acpgGenerate${variant.name.capitalize()}ContentProvider", type: GenerateContentProviderTask) {
                    entitiesDir acpgExtension.entitiesDir
                    outputDir sourceFolder
                    config acpgExtension.config
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

