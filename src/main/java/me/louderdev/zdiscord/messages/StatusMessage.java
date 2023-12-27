package me.louderdev.zdiscord.messages;

import me.louderdev.zdiscord.utils.file.ConfigFile;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class StatusMessage implements IMessage {

    private String topTitle, topTitleUrl, title, imgUrl, online, offline, footer, footerUrl;

    public StatusMessage load(ConfigFile c) {
        topTitle = c.getString("MESSAGE.TOP_TITLE");
        topTitleUrl = c.getString("MESSAGE.TOP_TITLE_URL");
        title = c.getString("MESSAGE.TITLE");
        imgUrl = c.getString("MESSAGE.IMG_URL");
        online = c.getString("MESSAGE.ONLINE");
        offline = c.getString("MESSAGE.OFFLINE");
        footer = c.getString("MESSAGE.FOOTER");
        footerUrl = c.getString("MESSAGE.FOOTER_URL");

        return this;
    }

    @Override
    public String getId() {
        return "server_status";
    }

    @Override
    public EmbedBuilder createEmbed(boolean isOnline) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(title);

        eb.setColor(isOnline ? Color.green : Color.red);

        eb.setDescription(isOnline ? online : offline);

        eb.setAuthor(topTitle, null, topTitleUrl);

        eb.setFooter(footer, footerUrl);
        eb.setThumbnail(imgUrl);
//        eb.setImage("https://github.com/zekroTJA/DiscordBot/blob/master/.websrc/logo%20-%20title.png");
//        eb.setThumbnail("https://github.com/zekroTJA/DiscordBot/blob/master/.websrc/logo%20-%20title.png");

        return eb;
    }
}
