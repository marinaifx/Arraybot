package de.arraying.arraybot.misc;

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
public enum BypassType {

    /**
     * The bypass applies in the text channel.
     */
    CHANNEL,

    /**
     * The bypass applies to the user.
     */
    USER,

    /**
     * The bypass applies to all users with the role.
     */
    ROLE

}
