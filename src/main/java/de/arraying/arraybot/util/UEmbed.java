package de.arraying.arraybot.util;

import de.arraying.arraybot.language.Message;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;

import java.awt.*;
import java.util.Calendar;

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
public final class UEmbed {

    private static final String icon = "http://i.imgur.com/yA9rB2j.png";

    /**
     * Gets the embed builder.
     * @param channel The text channel.
     * @return The embed.
     */
    public static CustomEmbedBuilder getEmbed(TextChannel channel) {
        Calendar calendar = Calendar.getInstance();
        JDA jda = channel.getJDA();
        Guild guild = channel.getGuild();
        int year = calendar.get(Calendar.YEAR);
        CustomEmbedBuilder embedBuilder = new CustomEmbedBuilder()
                .setAuthor("Arraybot", "http://arraybot.xyz",
                        shouldImage(channel.getGuild()) ? icon : null)
                .setColor(new Color(34, 150, 245))
                .setFooter(Message.EMBED_FOOTER.getContent(channel, String.valueOf(year)),
                        shouldImage(channel.getGuild()) ? jda.getSelfUser().getAvatarUrl() : null);
        if(shouldImage(guild)) {
            embedBuilder.setThumbnail(icon);
        }
        return embedBuilder;
    }

    /**
     * Whether or not the embed should contain images.
     * @param guild The guild.
     * @return True if it should, false otherwise.
     */
    private static boolean shouldImage(Guild guild) {
        return guild.getExplicitContentLevel() == Guild.ExplicitContentLevel.OFF;
    }

}
