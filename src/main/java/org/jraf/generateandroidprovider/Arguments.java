/*
 * This source is part of the
 *      _____  ___   ____
 *  __ / / _ \/ _ | / __/___  _______ _
 * / // / , _/ __ |/ _/_/ _ \/ __/ _ `/
 * \___/_/|_/_/ |_/_/ (_)___/_/  \_, /
 *                              /___/
 * repository.
 *
 * Copyright 2012 Benoit 'BoD' Lubek (BoD@JRAF.org).  All Rights Reserved.
 */
package org.jraf.generateandroidprovider;

import java.io.File;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = " =")
public class Arguments {
    public static String DEFAULT_TITLE = "GenerateAndroidProvider";

    @Parameter(names = { "-h", "--help" }, description = "Display this help and exit")
    public boolean help;

    @Parameter(names = { "-i", "--input" }, description = "Input folder, where to find _config.json and your entity json files")
    public File inputDir = new File(".");

    @Parameter(names = { "-o", "--output" }, description = "Output folder, where the resulting files will be generated")
    public File outputDir = new File("generated");
}
