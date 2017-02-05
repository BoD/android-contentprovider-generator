package org.jraf.acpg.gradleplugin

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.jraf.acpg.lib.Generator
import org.jraf.acpg.lib.GeneratorException

class GenerateContentProvider extends DefaultTask {
    @OutputDirectory
    File outputDir

    @InputDirectory
    File inputDir

    @TaskAction
    void generate() {
        try {
            new Generator(inputDir, outputDir).generate()
        } catch (GeneratorException e) {
            throw new GradleException('Problem while generating the ContentProvider.', e)
        }
    }
}