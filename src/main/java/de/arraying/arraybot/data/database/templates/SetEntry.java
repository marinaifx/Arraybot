package de.arraying.arraybot.data.database.templates;

import de.arraying.arraybot.data.database.Redis;
import de.arraying.arraybot.data.database.core.Entry;
import de.arraying.arraybot.util.UDatabase;
import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * Copyright 2017 Arraying
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public final class SetEntry implements Entry {

    private final Redis redis;
    private Category category;

    /**
     * Creates a new set entry.
     */
    public SetEntry() {
        this.redis = Redis.getInstance();
    }

    /**
     * Gets the entry type.
     * @return The type.
     */
    @Override
    public Type getType() {
        return Type.SET;
    }

    /**
     * Sets the category.
     * @param category The category.
     */
    @Override
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Gets the entries of the set.
     * @param id The snowflake identifier ID.
     * @return A set of entries. Cannot be null.
     */
    public Set<String> values(long id) {
        Jedis resource = redis.getJedisResource();
        Set<String> members = resource.smembers(UDatabase.getKey(category, id));
        resource.close();
        return members;
    }

    /**
     * Adds a member to the set.
     * @param id The snowflake identifier ID.
     * @param entry The entry. Cannot be null.
     */
    public void add(long id, Object entry) {
        Jedis resource = redis.getJedisResource();
        resource.sadd(UDatabase.getKey(category, id), entry.toString());
        resource.close();
    }

    /**
     * Removes an entry from the set,
     * @param id The snowflake identifier ID.
     * @param entry The entry. Cannot be null.
     */
    public void remove(long id, Object entry) {
        Jedis resource = redis.getJedisResource();
        resource.srem(UDatabase.getKey(category, id), entry.toString());
        resource.close();
    }

}
