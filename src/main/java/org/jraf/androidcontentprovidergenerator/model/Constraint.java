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
package org.jraf.androidcontentprovidergenerator.model;

public class Constraint {
    public static class Json {
        public static final String NAME = "name";
        public static final String DEFINITION = "definition";
    }

    private final String mName;
    private final String mDefinition;

    public Constraint(String name, String definition) {
        mName = name;
        mDefinition = definition;
    }

    public String getName() {
        return mName;
    }

    public String getDefinition() {
        return mDefinition;
    }

    @Override
    public String toString() {
        return "Constraint [mName=" + mName + ", mDefinition=" + mDefinition + "]";
    }
}
