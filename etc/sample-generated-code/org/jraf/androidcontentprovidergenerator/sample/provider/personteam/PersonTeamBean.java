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
package org.jraf.androidcontentprovidergenerator.sample.provider.personteam;

// @formatter:off
import org.jraf.androidcontentprovidergenerator.sample.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Entity joining people and teams.  A team contains several people, and a person can belong to several teams.
 */
@SuppressWarnings({"WeakerAccess", "unused", "ConstantConditions"})
public class PersonTeamBean implements PersonTeamModel {
    private long mId;
    private long mPersonId;
    private long mTeamId;

    /**
     * Primary key.
     */
    @Override
    public long getId() {
        return mId;
    }

    /**
     * Primary key.
     */
    public void setId(long id) {
        mId = id;
    }

    /**
     * Get the {@code person_id} value.
     */
    @Override
    public long getPersonId() {
        return mPersonId;
    }

    /**
     * Set the {@code person_id} value.
     */
    public void setPersonId(long personId) {
        mPersonId = personId;
    }

    /**
     * Get the {@code team_id} value.
     */
    @Override
    public long getTeamId() {
        return mTeamId;
    }

    /**
     * Set the {@code team_id} value.
     */
    public void setTeamId(long teamId) {
        mTeamId = teamId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonTeamBean bean = (PersonTeamBean) o;
        return mId == bean.mId;
    }

    @Override
    public int hashCode() {
        return (int) (mId ^ (mId >>> 32));
    }

    /**
     * Instantiate a new PersonTeamBean with specified values.
     */
    @NonNull
    public static PersonTeamBean newInstance(long id, long personId, long teamId) {
        PersonTeamBean res = new PersonTeamBean();
        res.mId = id;
        res.mPersonId = personId;
        res.mTeamId = teamId;
        return res;
    }

    /**
     * Instantiate a new PersonTeamBean with all the values copied from the given model.
     */
    @NonNull
    public static PersonTeamBean copy(@NonNull PersonTeamModel from) {
        PersonTeamBean res = new PersonTeamBean();
        res.mId = from.getId();
        res.mPersonId = from.getPersonId();
        res.mTeamId = from.getTeamId();
        return res;
    }

    public static class Builder {
        private PersonTeamBean mRes = new PersonTeamBean();

        /**
         * Primary key.
         */
        public Builder id(long id) {
            mRes.mId = id;
            return this;
        }

        /**
         * Set the {@code person_id} value.
         */
        public Builder personId(long personId) {
            mRes.mPersonId = personId;
            return this;
        }

        /**
         * Set the {@code team_id} value.
         */
        public Builder teamId(long teamId) {
            mRes.mTeamId = teamId;
            return this;
        }

        /**
         * Get a new PersonTeamBean built with the given values.
         */
        public PersonTeamBean build() {
            return mRes;
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }
}
