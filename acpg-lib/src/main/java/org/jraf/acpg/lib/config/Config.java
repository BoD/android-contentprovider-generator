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
package org.jraf.acpg.lib.config;

import java.io.Serializable;

public class Config implements Serializable, Cloneable {
    public Integer syntaxVersion;
    public String packageName;
    public String authority;
    public String providerJavaPackage;
    public String providerClassName;
    public String sqliteOpenHelperClassName;
    public String sqliteOpenHelperCallbacksClassName;
    public String databaseFileName;
    public Integer databaseVersion;
    public Boolean enableForeignKeys;
    public Boolean useAnnotations;
    public Boolean useSupportLibrary;
    public Boolean generateBeans;
    public String debugLogsFieldName;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Config config = (Config) o;

        if (syntaxVersion != null ? !syntaxVersion.equals(config.syntaxVersion) : config.syntaxVersion != null) return false;
        if (packageName != null ? !packageName.equals(config.packageName) : config.packageName != null) return false;
        if (authority != null ? !authority.equals(config.authority) : config.authority != null) return false;
        if (providerJavaPackage != null ? !providerJavaPackage.equals(config.providerJavaPackage) : config.providerJavaPackage != null) return false;
        if (providerClassName != null ? !providerClassName.equals(config.providerClassName) : config.providerClassName != null) return false;
        if (sqliteOpenHelperClassName != null ? !sqliteOpenHelperClassName.equals(config.sqliteOpenHelperClassName) : config.sqliteOpenHelperClassName != null)
            return false;
        if (sqliteOpenHelperCallbacksClassName != null ? !sqliteOpenHelperCallbacksClassName.equals(config.sqliteOpenHelperCallbacksClassName) :
                config.sqliteOpenHelperCallbacksClassName != null) return false;
        if (databaseFileName != null ? !databaseFileName.equals(config.databaseFileName) : config.databaseFileName != null) return false;
        if (databaseVersion != null ? !databaseVersion.equals(config.databaseVersion) : config.databaseVersion != null) return false;
        if (enableForeignKeys != null ? !enableForeignKeys.equals(config.enableForeignKeys) : config.enableForeignKeys != null) return false;
        if (useAnnotations != null ? !useAnnotations.equals(config.useAnnotations) : config.useAnnotations != null) return false;
        if (useSupportLibrary != null ? !useSupportLibrary.equals(config.useSupportLibrary) : config.useSupportLibrary != null) return false;
        if (generateBeans != null ? !generateBeans.equals(config.generateBeans) : config.generateBeans != null) return false;
        return debugLogsFieldName != null ? debugLogsFieldName.equals(config.debugLogsFieldName) : config.debugLogsFieldName == null;
    }

    @Override
    public int hashCode() {
        int result = syntaxVersion != null ? syntaxVersion.hashCode() : 0;
        result = 31 * result + (packageName != null ? packageName.hashCode() : 0);
        result = 31 * result + (authority != null ? authority.hashCode() : 0);
        result = 31 * result + (providerJavaPackage != null ? providerJavaPackage.hashCode() : 0);
        result = 31 * result + (providerClassName != null ? providerClassName.hashCode() : 0);
        result = 31 * result + (sqliteOpenHelperClassName != null ? sqliteOpenHelperClassName.hashCode() : 0);
        result = 31 * result + (sqliteOpenHelperCallbacksClassName != null ? sqliteOpenHelperCallbacksClassName.hashCode() : 0);
        result = 31 * result + (databaseFileName != null ? databaseFileName.hashCode() : 0);
        result = 31 * result + (databaseVersion != null ? databaseVersion.hashCode() : 0);
        result = 31 * result + (enableForeignKeys != null ? enableForeignKeys.hashCode() : 0);
        result = 31 * result + (useAnnotations != null ? useAnnotations.hashCode() : 0);
        result = 31 * result + (useSupportLibrary != null ? useSupportLibrary.hashCode() : 0);
        result = 31 * result + (generateBeans != null ? generateBeans.hashCode() : 0);
        result = 31 * result + (debugLogsFieldName != null ? debugLogsFieldName.hashCode() : 0);
        return result;
    }
}
