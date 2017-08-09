package de.arraying.arraybot.core.iface

import de.arraying.arraybot.cache.entities.CPunishment
import net.dv8tion.jda.core.entities.Guild
import net.dv8tion.jda.core.entities.Member

/**
 * Copyright 2017 Arraying
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
interface IPunishment {

    /**
     * Invokes the punishment.
     * Returns false if unsuccessful.
     */
    fun invoke(guild: Guild, id: Long, member: Member, reason: String): Boolean

    /**
     * Revokes the punishment.
     * Returns false if this punishment cannot be revoked.
     */
    fun revoke(guild: Guild, punishment: CPunishment, manual: Boolean = false): Boolean {
        return false
    }

}