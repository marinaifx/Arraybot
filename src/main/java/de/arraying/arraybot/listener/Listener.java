package de.arraying.arraybot.listener;

import de.arraying.arraybot.listener.listeners.PostLoadListener;
import de.arraying.arraybot.listener.listeners.postload.DeathListener;
import de.arraying.arraybot.listener.listeners.postload.GuildListener;
import de.arraying.arraybot.listener.listeners.postload.MessageListener;
import de.arraying.arraybot.request.requests.CarbonitexRequest;
import de.arraying.arraybot.request.requests.DiscordOrgRequest;
import de.arraying.arraybot.request.requests.DiscordPwRequest;
import de.arraying.arraybot.threadding.AbstractTask;
import net.dv8tion.jda.core.JDA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

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
public final class Listener {

    /**
     * The global listener logger.
     */
    public static final Logger LISTENER_LOGGER = LoggerFactory.getLogger("Listener");

    /**
     * An array of all listeners that will be registered after the shard has loaded.
     */
    public static final PostLoadListener[] POST_LOAD_LISTENERS = { new DeathListener(), new GuildListener(), new MessageListener()};

    /**
     * The updater class that will handle updates.
     */
    public static final class Updater extends AbstractTask {

        private final JDA shard;

        /**
         * Creates a new guild count updater.
         * @param shard The shard that needs updating.
         */
        public Updater(JDA shard) {
            super("Guild-Updater");
            this.shard = shard;
        }

        /**
         * Updates the server counts.
         */
        @Override
        public void onExecution() {
            try {
                new CarbonitexRequest(shard).sendRequest();
                new DiscordOrgRequest(shard).sendRequest();
                new DiscordPwRequest(shard).sendRequest();
            } catch(IOException exception) {
                logger.error("Encountered an error.", exception);
            }
        }

    }

}
