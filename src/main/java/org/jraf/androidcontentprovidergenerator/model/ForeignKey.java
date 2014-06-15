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

import org.jraf.androidcontentprovidergenerator.model.Field.OnDeleteAction;

public class ForeignKey {
    private final String mEntityName;
    private final OnDeleteAction mOnDeleteAction;

    public ForeignKey(String entityName, OnDeleteAction onDeleteAction) {
        mEntityName = entityName;
        mOnDeleteAction = onDeleteAction;
    }

    public String getEntityName() {
        return mEntityName;
    }

    public Entity getEntity() {
        return Entity.getByName(mEntityName);
    }

    public Field getField() {
        return getEntity().getFieldByName("_id");
    }

    public OnDeleteAction getOnDeleteAction() {
        return mOnDeleteAction;
    }

    @Override
    public String toString() {
        return "ForeignKey [mEntityName=" + mEntityName + ", mOnDeleteAction=" + mOnDeleteAction + "]";
    }
}