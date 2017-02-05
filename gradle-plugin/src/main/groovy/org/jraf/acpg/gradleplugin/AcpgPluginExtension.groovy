package org.jraf.acpg.gradleplugin

import org.gradle.api.Project

class AcpgPluginExtension {
    private final Project project

    AcpgPluginExtension(Project project) {
        this.project = project
    }

    def inputDir = project.parent.file('etc/acpg')
}
