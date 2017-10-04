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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jraf.androidcontentprovidergenerator.Constants;
import org.jraf.androidcontentprovidergenerator.Log;

public class Model {
    private static final String TAG = Constants.TAG + Model.class.getSimpleName();

    private static final Model INSTANCE = new Model();

    public static Model get() {
        return INSTANCE;
    }

    private Model() {}

    private final List<Entity> mEntities = new ArrayList<Entity>();
    private String mHeader;

    public void addEntity(Entity entity) {
        mEntities.add(entity);
    }

    public List<Entity> getEntities() {
        return Collections.unmodifiableList(mEntities);
    }

    public void setHeader(String header) {
        mHeader = header;
    }

    public String getHeader() {
        return mHeader;
    }

    @Override
    public String toString() {
        return mEntities.toString();
    }

    public void flagAmbiguousFields() {
        for (Entity entity : mEntities) {
            entity.flagAmbiguousFields();
        }

        for (Entity entity : mEntities) {
            for (Field field : entity.getFields()) {
                if (field.getIsAmbiguous()) {
                    Log.i(TAG,
                            "\nNote: in the table '" + entity.getNameLowerCase() + "', the column '" + field.getName() + "' will be named '"
                                    + field.getPrefixedName() + "' to avoid ambiguities when joining.\n");
                }
            }
        }
    }
}
