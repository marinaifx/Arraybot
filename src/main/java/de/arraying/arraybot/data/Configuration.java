package de.arraying.arraybot.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;

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
public final class Configuration {

    private static final Logger logger = LoggerFactory.getLogger("Configuration");

    private final String botToken;
    private final String botBetaToken;
    private final int botShards;
    private final long[] botAuthors;
    private final String botPrefix;
    private final String botVersion;
    private final boolean botBeta;
    private final String botLanguage;
    private final String redisHost;
    private final int redisPort;
    private final String redisAuth;
    private final int redisIndex;
    private final String keyCarbonitex;
    private final String keyDiscordOrg;
    private final String keyDiscordPw;
    private final String announcement;


    /**
     * Creates a new bot configuration.
     * @param botToken The bot's token.
     * @param botBetaToken The beta bot's token.
     * @param botShards The amount of shards to use.
     * @param botAuthors The bot author(s).
     * @param botPrefix The default prefix.
     * @param botVersion The bot version.
     * @param botBeta Whether or not to use the beta bot.
     * True = Use beta.
     * @param botLanguage The default language to use for the bot.
     * @param redisHost The Redis hostname/IP.
     * @param redisPort The Redis port.
     * @param redisAuth The Redis authentication string.
     * Can be empty.
     * @param redisIndex The Redis index to use.
     * @param keyCarbonitex The Carbonitex.net secret key.
     * @param keyDiscordOrg The DiscordBots.org secret key.
     * @param keyDiscordPw The Bots.Discord.pw secret key.
     * @param announcement The current announcement.
     */
    private Configuration(String botToken, String botBetaToken, int botShards, long[] botAuthors, String botPrefix, String botVersion, String botLanguage, boolean botBeta, String redisHost, int redisPort, String redisAuth, int redisIndex, String keyCarbonitex, String keyDiscordOrg, String keyDiscordPw, String announcement) {
        this.botToken = botToken;
        this.botBetaToken = botBetaToken;
        this.botShards = botShards;
        this.botAuthors = botAuthors;
        this.botPrefix = botPrefix;
        this.botVersion = botVersion;
        this.botLanguage = botLanguage;
        this.botBeta = botBeta;
        this.redisHost = redisHost;
        this.redisPort = redisPort;
        this.redisAuth = redisAuth;
        this.redisIndex = redisIndex;
        this.keyCarbonitex = keyCarbonitex;
        this.keyDiscordOrg = keyDiscordOrg;
        this.keyDiscordPw = keyDiscordPw;
        this.announcement = announcement;
    }

    /**
     * Gets the Configuration.
     * @param file The configuration file.
     * @throws ConfigurationException If an exception occurs.
     */
    public static Configuration getConfiguration(File file) throws ConfigurationException {
        if(file.exists()) {
            return load(file);
        } else {
            create(file);
            throw new ConfigurationException("The configuration has been created and must be filled in.");
        }
    }

    /**
     * Loads the configuration.
     * @param file The file to use as a configuration file.
     * @return Returns a data configuration.
     * @throws ConfigurationException If an exception occurs.
     */
    private static Configuration load(File file) throws ConfigurationException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            reader.close();
            String jsonString = stringBuilder.toString();
            JSONObject jsonObject = new JSONObject(jsonString);
            LinkedList<Object> constructorParameters = new LinkedList<>();
            Constructor constructor = Configuration.class.getDeclaredConstructors()[0];
            for(ConfigEntry entry : ConfigEntry.values()) {
                String key = entry.jsonKey;
                Object entryObject;
                if(jsonObject.has(key)) {
                    switch(entry.type) {
                        case STRING:
                            entryObject = jsonObject.getString(key);
                            break;
                        case INT:
                            entryObject = jsonObject.getInt(key);
                            break;
                        case LONG:
                            entryObject = jsonObject.getLong(key);
                            break;
                        case BOOLEAN:
                            entryObject = jsonObject.getBoolean(key);
                            break;
                        case ARRAY_LONG:
                            JSONArray arrayLong = jsonObject.getJSONArray(key);
                            long[] entries = new long[arrayLong.length()];
                            for(int i = 0; i < arrayLong.length(); i++) {
                                entries[i] = arrayLong.getLong(i);
                            }
                            entryObject = entries;
                            break;
                        default:
                            throw new IllegalStateException("Something went very badly wrong with the config entry.");
                    }
                } else {
                    Object defaultValue = entry.defaultValue;
                    entryObject = entry.type == ConfigEntry.ConfigEntryType.ARRAY_LONG ? new long[] {} : defaultValue;
                    logger.error("Could not find the entry for the key \"{}" + "\", thus using the default value \"{}\".", entry.jsonKey, entry.defaultValue);
                }
                constructorParameters.add(entryObject);
            }
            if(constructorParameters.size() != constructor.getParameterCount()) {
                throw new IllegalStateException("The DataClass constructor has a different number of parameters than the ones defined in the entry set.");
            }
            constructor.setAccessible(true);
            Configuration config = (Configuration) constructor.newInstance(constructorParameters.toArray());
            if(!config.isValid()) {
                throw new IllegalStateException("The configuration file is invalid.");
            }
            return config;
        } catch(IOException | InstantiationException | IllegalAccessException | InvocationTargetException | JSONException exception) {
            throw new ConfigurationException(exception.getMessage());
        }
    }

    /**
     * Creates the configuration.
     * @param file The file to use as a configuration file.
     * @throws ConfigurationException If an I/O error occurs.
     */
    private static void create(File file) throws ConfigurationException {
        JSONObject jsonObject = new JSONObject();
        for(ConfigEntry entry : ConfigEntry.values()) {
            jsonObject.put(entry.jsonKey, entry.defaultValue);
        }
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(jsonObject.toString());
            writer.close();
        } catch(IOException exception) {
            throw new ConfigurationException(exception.getMessage());
        }
    }

    /**
     * Checks whether the configuration is valid.
     * @return True if it is valid, else false.
     */
    private boolean isValid() {
        return ((botBeta && !botBetaToken.isEmpty()) || (!botBeta && !botToken.isEmpty())) && botShards > 0 && botAuthors.length > 0 && !botPrefix.isEmpty() && !botVersion.isEmpty() && !botLanguage.isEmpty() && !redisHost.isEmpty() && (redisPort > 1024 && redisPort < 49151) && (redisIndex >= 0 && redisIndex <= 16);
    }

    /**
     * Gets the bot token.
     * @return The bot token.
     */
    public String getBotToken() {
        return botToken;
    }

    /**
     * Gets the bot beta token.
     * @return The bot beta token.
     */
    public String getBotBetaToken() {
        return botBetaToken;
    }

    /**
     * Gets the number of shards.
     * @return The shard number. >= 1.
     */
    public int getBotShards() {
        return botShards;
    }

    /**
     * Gets an array of author IDs.
     * @return The authors.
     */
    public long[] getBotAuthors() {
        return botAuthors;
    }

    /**
     * Gets the default bot prefix.
     * @return The bot prefix.
     */
    public String getBotPrefix() {
        return botPrefix;
    }

    /**
     * Gets the bot version.
     * @return The bot version.
     */
    public String getBotVersion() {
        return botVersion;
    }

    /**
     * Whether or not to use the beta token.
     * @return True = beta, false = regular.
     */
    public boolean isBotBeta() {
        return botBeta;
    }

    /**
     * Gets the default bot language.
     * @return The bot language.
     */
    public String getBotLanguage() {
        return botLanguage;
    }

    /**
     * Gets the Redis host.
     * @return The Redis host.
     */
    public String getRedisHost() {
        return redisHost;
    }

    /**
     * Gets the Redis port.
     * @return The Redis port.
     */
    public int getRedisPort() {
        return redisPort;
    }

    /**
     * Gets the Redis password.
     * @return The Redis password.
     */
    public String getRedisAuth() {
        return redisAuth;
    }

    /**
     * Gets the Redis index.
     * @return The Redis index.
     */
    public int getRedisIndex() {
        return redisIndex;
    }

    /**
     * Gets the Carbonitex secret key.
     * @return The secret key.
     */
    public String getKeyCarbonitex() {
        return keyCarbonitex;
    }

    /**
     * Gets the discordbots.org token.
     * @return The token.
     */
    public String getKeyDiscordOrg() {
        return keyDiscordOrg;
    }

    /**
     * Gets the bots.discord.pw token.
     * @return The token.
     */
    public String getKeyDiscordPw() {
        return keyDiscordPw;
    }

    /**
     * Gets the announcement.
     * @return The announcement.
     */
    public String getAnnouncement() {
        return announcement;
    }

    @SuppressWarnings("unused")
    private enum ConfigEntry {

        BOT_TOKEN("bot-token", ConfigEntry.ConfigEntryType.STRING, ""), BOT_BETA_TOKEN("bot-beta-token", ConfigEntry.ConfigEntryType.STRING, ""), BOT_SHARDS("bot-shards", ConfigEntry.ConfigEntryType.INT, 1), BOT_AUTHORS("bot-authors", ConfigEntry.ConfigEntryType.ARRAY_LONG, new JSONArray()), BOT_PREFIX("bot-prefix", ConfigEntry.ConfigEntryType.STRING, "//"), BOT_VERSION("bot-version", ConfigEntry.ConfigEntryType.STRING, "0.0.0"), BOT_LANGUAGE("bot-language", ConfigEntry.ConfigEntryType.STRING, "en"), BOT_BETA("bot-beta", ConfigEntry.ConfigEntryType.BOOLEAN, true), REDIS_HOST("redis-host", ConfigEntry.ConfigEntryType.STRING, "localhost"), REDIS_PORT("redis-port", ConfigEntry.ConfigEntryType.INT, 6379), REDIS_AUTH("redis-auth", ConfigEntry.ConfigEntryType.STRING, ""), REDIS_INDEX("redis-index", ConfigEntry.ConfigEntryType.INT, 5), KEY_CARBONITEX("key-carbonitex", ConfigEntryType.STRING, ""), KEY_DISCORD_ORG("key-discord-org", ConfigEntryType.STRING, ""), KEY_DISCORD_PW("key-discord-pw", ConfigEntryType.STRING, ""), ANNOUNCEMENT("announcement", ConfigEntryType.STRING, "");

        private final String jsonKey;
        private final ConfigEntry.ConfigEntryType type;
        private final Object defaultValue;

        /**
         * Creates a new config entry.
         * @param jsonKey The JSON key for the entry.
         * @param type The type of entry.
         * @param defaultValue The default value.
         */
        ConfigEntry(String jsonKey, ConfigEntry.ConfigEntryType type, Object defaultValue) {
            this.jsonKey = jsonKey;
            this.type = type;
            this.defaultValue = defaultValue;
        }

        private enum ConfigEntryType {

            STRING, INT, LONG, BOOLEAN, ARRAY_LONG,

        }

    }

    public static class ConfigurationException extends Exception {

        /**
         * Creates a new configuration exception.
         * @param message The exception message.
         */
        ConfigurationException(String message) {
            super(message);
        }

    }

}
