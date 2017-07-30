package de.arraying.arraybot.commands.command.custom.collections

import de.arraying.arraybot.commands.command.custom.entities.CustomCommandTypes
import de.arraying.arraybot.commands.command.custom.types.*
import de.arraying.arraybot.core.iface.ICustomCommandType

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
enum class CustomCommandTypeCollection(val type: ICustomCommandType,
                                       val types: CustomCommandTypes) {

    ADD_ROLE(CustomCommandTypeAddRole(), CustomCommandTypes.ADDROLE),
    MESSAGE(CustomCommandTypeMessage(), CustomCommandTypes.MESSAGE),
    NICKNAME(CustomCommandTypeNickname(), CustomCommandTypes.NICKNAME),
    PRIVATE_MESSAGE(CustomCommandTypePrivateMessage(), CustomCommandTypes.PRIVATEMESSAGE),
    REMOVE_ROLE(CustomCommandTypeRemoveRole(), CustomCommandTypes.REMOVEROLE),
    TOGGLE_ROLE(CustomCommandTypeToggleRole(), CustomCommandTypes.TOGGLEROLE)

}