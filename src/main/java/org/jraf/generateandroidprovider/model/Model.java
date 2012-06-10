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
package org.jraf.generateandroidprovider.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Model {
    private static Model sModel;

    public synchronized static Model get() {
        if (sModel == null) {
            sModel = new Model();
        }
        return sModel;
    }

    private Model() {}

    private final List<Entity> mEntities = new ArrayList<Entity>();

    public void addEntity(Entity entity) {
        mEntities.add(entity);
    }

    public List<Entity> getEntities() {
        return Collections.unmodifiableList(mEntities);
    }

    @Override
    public String toString() {
        return mEntities.toString();
    }
}
