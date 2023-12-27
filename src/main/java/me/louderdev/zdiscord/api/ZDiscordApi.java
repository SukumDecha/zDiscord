package me.louderdev.zdiscord.api;

import me.louderdev.zdiscord.ZDiscord;
import me.louderdev.zdiscord.messages.IMessage;
import me.louderdev.zdiscord.messages.StatusMessage;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.awt.*;

public class ZDiscordApi {

    private static final ZDiscord INSTANCE = ZDiscord.getInstance();
    private static final long GUILD_ID = INSTANCE.getBotSetting().getGuildId();
    private static final long STATUS_ID = INSTANCE.getBotSetting().getStatusId();

    private static JDA getBot() {
        return INSTANCE.getJda();
    }

    private static TextChannel getTextChannelById(long channelId) {
        Guild guild = getBot().getGuildById(GUILD_ID);
        return (guild != null) ? guild.getTextChannelById(channelId) : null;
    }

    private static void sendMessageToChannel(long channelId, EmbedBuilder builder) {
        TextChannel textChannel = getTextChannelById(channelId);
        if (textChannel != null) {
            textChannel.sendMessageEmbeds(builder.build()).queue();
        } else {
            System.err.println("Text channel not found with ID: " + channelId);
        }
    }

    private static void sendMessageToRole(long channelId, Role role, EmbedBuilder builder) {
        TextChannel textChannel = getTextChannelById(channelId);
        if (textChannel != null) {
            textChannel.sendMessage("TAG:"  + role.getAsMention()).addEmbeds(builder.build()).queue();
        } else {
            System.err.println("Text channel not found with ID: " + channelId);
        }
    }

    public static void sendMessage(long channelId, EmbedBuilder builder) {
        JDA jda = getBot();
        if (jda != null) {
            sendMessageToChannel(channelId, builder);
        } else {
            System.err.println("JDA instance is null.");
        }
    }

    public static void broadcastMessage(long channelId, EmbedBuilder builder) {
        JDA jda = getBot();
        if (jda != null) {
            Role publicRole = jda.getRoleById(GUILD_ID);
            if (publicRole != null) {
                sendMessageToRole(channelId, publicRole, builder);
            } else {
                System.err.println("Public role not found in guild with ID: " + GUILD_ID);
            }
        } else {
            System.err.println("JDA instance is null.");
        }
    }

    public static void sendServerStatus(boolean online) {
        IMessage<StatusMessage> m = INSTANCE.getMessageHandler().getById("server_status");
        broadcastMessage(STATUS_ID, m.createEmbed(online));
    }
}
