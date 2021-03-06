package de.arraying.arraybot.util;

import de.arraying.arraybot.language.Message;
import de.arraying.zeus.std.method.methods.OutputMethods;
import net.dv8tion.jda.core.entities.TextChannel;

import java.lang.reflect.Method;

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
public final class UZeus {

    /**
     * Gets all ignored methods.
     * @return An array of methods.
     */
    public static Method[] getIgnored() {
        return OutputMethods.class.getMethods();
    }

    /**
     * Sends a standard error.
     * @param channel The channel to send the error in.
     * @param lineNumber The line number.
     * @param message The message.
     */
    public static void error(TextChannel channel, int lineNumber, String message) {
        Message.ZEUS_ERROR.send(channel, String.valueOf(lineNumber), message).queue();
    }

    /**
     * Sends an error in the channel.
     * @param channel The channel.
     * @param exception The exception.
     */
    public static void errorInChannel(TextChannel channel, Exception exception) {
        Message.ZEUS_ERROR.send(channel, "<" + Message.ZEUS_ERROR_PROVIDED.getContent(channel) + ">",
                exception.getMessage() + ".").queue();
    }

}
