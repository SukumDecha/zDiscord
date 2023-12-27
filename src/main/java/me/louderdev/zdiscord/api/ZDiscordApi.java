package me.louderdev.zdiscord.api;

import me.louderdev.zdiscord.ZDiscord;
import me.louderdev.zdiscord.messages.IMessage;
import me.louderdev.zdiscord.messages.StatusMessage;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.Bukkit;

import java.awt.*;

public class ZDiscordApi {

    public static final long guildId = ZDiscord.getInstance().getBotSetting().getGuildId();
    public static final long statusId = ZDiscord.getInstance().getBotSetting().getStatusId();


    public static JDA getBot() {
        return ZDiscord.getInstance().getJda();
    }

    public static void sendMessage(long channelId, EmbedBuilder builder) {
        JDA jda = getBot();
        if (jda != null) {
            Guild guild = jda.getGuildById(guildId);
            if (guild != null) {
                TextChannel textChannel = guild.getTextChannelById(channelId);
                if (textChannel != null) {
                    textChannel.sendMessageEmbeds(builder.build()).queue();
                } else {
                    System.err.println("Text channel not found with ID: " + channelId);
                }
            } else {
                System.err.println("Guild not found with ID: " + guildId);
            }
        } else {
            System.err.println("JDA instance is null.");
        }
    }

    public static void broadcastMessage(long channelId, EmbedBuilder builder) {
        JDA jda = getBot();
        if (jda != null) {
            Guild guild = jda.getGuildById(guildId);
            if (guild != null) {
                TextChannel textChannel = guild.getTextChannelById(channelId);
                if (textChannel != null) {
                    textChannel.sendMessage(guild.getPublicRole().getAsMention())
                            .addEmbeds(builder.build()).queue();
                } else {
                    System.err.println("Text channel not found with ID: " + channelId);
                }
            } else {
                System.err.println("Guild not found with ID: " + guildId);
            }
        } else {
            System.err.println("JDA instance is null.");
        }
    }

    public static void sendServerStatus(boolean online) {
        IMessage<StatusMessage> m = ZDiscord.getInstance().getMessageHandler().getById("server_status");
        broadcastMessage(statusId, m.createEmbed(online));
    }
}
