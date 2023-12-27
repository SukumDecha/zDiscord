package me.louderdev.zdiscord.messages;

import me.louderdev.zdiscord.utils.file.ConfigFile;
import net.dv8tion.jda.api.EmbedBuilder;

public interface IMessage<T> {

    IMessage<T> load(ConfigFile c);

    String getId();

    EmbedBuilder createEmbed(boolean online);
}
