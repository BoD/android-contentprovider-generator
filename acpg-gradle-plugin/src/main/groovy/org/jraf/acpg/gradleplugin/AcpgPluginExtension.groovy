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

import org.gradle.api.Project
import org.jraf.acpg.lib.config.Config

class AcpgPluginExtension {
    private final Project project
    final Config config = new Config()

    AcpgPluginExtension(Project project) {
        this.project = project
        config.syntaxVersion = 4
    }

    def entitiesDir = project.parent.file('etc/acpg')

    def syntaxVersion(int syntaxVersion) {
        config.syntaxVersion = syntaxVersion
    }

    def packageName(String packageName) {
        config.packageName = packageName
    }

    def authority(String authority) {
        config.authority = authority
    }

    def providerJavaPackage(String providerJavaPackage) {
        config.providerJavaPackage = providerJavaPackage
    }

    def providerClassName(String providerClassName) {
        config.providerClassName = providerClassName
    }

    def sqliteOpenHelperClassName(String sqliteOpenHelperClassName) {
        config.sqliteOpenHelperClassName = sqliteOpenHelperClassName
    }

    def sqliteOpenHelperCallbacksClassName(String sqliteOpenHelperCallbacksClassName) {
        config.sqliteOpenHelperCallbacksClassName = sqliteOpenHelperCallbacksClassName
    }

    def databaseFileName(String databaseFileName) {
        config.databaseFileName = databaseFileName
    }

    def databaseVersion(int databaseVersion) {
        config.databaseVersion = databaseVersion
    }

    def enableForeignKeys(boolean enableForeignKeys) {
        config.enableForeignKeys = enableForeignKeys
    }

    def useAnnotations(boolean useAnnotations) {
        config.useAnnotations = useAnnotations
    }

    def useSupportLibrary(boolean useSupportLibrary) {
        config.useSupportLibrary = useSupportLibrary
    }

    def generateBeans(boolean generateBeans) {
        config.generateBeans = generateBeans
    }

    def debugLogsFieldName(String debugLogsFieldName) {
        config.debugLogsFieldName = debugLogsFieldName
    }
}
