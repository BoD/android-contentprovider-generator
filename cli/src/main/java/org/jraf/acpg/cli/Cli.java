/*
 * This source is part of the
 *      _____  ___   ____
 *  __ / / _ \/ _ | / __/___  _______ _
 * / // / , _/ __ |/ _/_/ _ \/ __/ _ `/
 * \___/_/|_/_/ |_/_/ (_)___/_/  \_, /
 *                              /___/
 * repository.
 *
 * Copyright (C) 2012-2014 Benoit 'BoD' Lubek (BoD@JRAF.org)
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
package org.jraf.acpg.cli;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.beust.jcommander.JCommander;

import org.jraf.acpg.lib.Generator;
import org.jraf.acpg.lib.GeneratorException;
import org.jraf.acpg.lib.config.Config;

public class Cli {
    private static final Logger LOG = LogManager.getLogger(Cli.class);

    private void go(String[] args) {
        Arguments arguments = new Arguments();
        JCommander jCommander = new JCommander(arguments, args);
        jCommander.setProgramName("GenerateAndroidProvider");

        if (arguments.help) {
            jCommander.usage();
            return;
        }

        Config config = null;
        try {
            config = Generator.parseConfig(arguments.inputDir);
        } catch (GeneratorException e) {
            LOG.error("Problem while parsing the _config.json file.", e);
        }

        try {
            new Generator(config, arguments.inputDir, arguments.outputDir).generate();
        } catch (GeneratorException e) {
            LOG.error("Problem while generating the ContentProvider.", e);
        }
    }

    public static void main(String[] args) throws Exception {
        new Cli().go(args);
    }
}
