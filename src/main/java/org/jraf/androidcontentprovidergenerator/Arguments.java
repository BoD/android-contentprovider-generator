/*
 * This source is part of the
 *      _____  ___   ____
 *  __ / / _ \/ _ | / __/___  _______ _
 * / // / , _/ __ |/ _/_/ _ \/ __/ _ `/
 * \___/_/|_/_/ |_/_/ (_)___/_/  \_, /
 *                              /___/
 * repository.
 *
 * Copyright (C) 2012-2015 Benoit 'BoD' Lubek (BoD@JRAF.org)
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
package org.jraf.androidcontentprovidergenerator;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import java.io.File;

@Parameters(separators = " =")
public class Arguments {
    public static String DEFAULT_TITLE = "GenerateAndroidProvider";

    @Parameter(names = {"-h", "--help"}, description = "Display this help and exit")
    public boolean help;

    @Parameter(names = {"-i", "--input"}, description = "Input folder, where to find _config.json and your entity json files")
    public File inputDir = new File(".");

    @Parameter(names = {"-o", "--output"}, description = "Output folder, where the resulting files will be generated")
    public File outputDir = new File("generated");

    @Parameter(names = {"-c", "--custom"}, description = "Custom output folder, where customizable files will be generated")
    public File customDir;

    @Parameter(names = {"-l", "--library"}, description = "Generate for library (Do not generate provider and db helper classes)")
    public boolean library = false;

    @Parameter(names = {"-d", "--debug"}, description = "Generate debug code")
    public boolean debug = false;
}
