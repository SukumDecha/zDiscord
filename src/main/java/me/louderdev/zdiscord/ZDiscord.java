package me.louderdev.zdiscord;

import lombok.Getter;
import lombok.SneakyThrows;
import me.louderdev.zdiscord.api.ZDiscordApi;
import me.louderdev.zdiscord.bot.BotBuilder;
import me.louderdev.zdiscord.bot.BotSetting;
import me.louderdev.zdiscord.messages.MessageHandler;
import me.louderdev.zdiscord.utils.file.ConfigFile;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class ZDiscord extends JavaPlugin {

    @Getter private static ZDiscord instance;

    private JDA jda;
    private BotSetting botSetting;
    private MessageHandler messageHandler;
    private ConfigFile c;

    @Override
    public void onEnable() {
        instance = this;

        loadConfig();
        loadMessages();
        createBot();

        ZDiscordApi.sendServerStatus(true);
    }

    @Override
    public void onDisable() {
        ZDiscordApi.sendServerStatus(false);
    }

    @SneakyThrows
    private void loadConfig() {
        c = new ConfigFile(this, "config.yml");

        botSetting = new BotSetting();
        botSetting.setToken(c.getString("BOT.TOKEN"));
        botSetting.setActivity(c.getString("BOT.ACTIVITY"));
        botSetting.setGuildId(c.getLong("GUILD_ID"));
        botSetting.setStatusId(c.getLong("CHANNEL_ID.STATUS_LOGGER"));

        getLogger().info("Loaded Bot settings...");
    }

    private void loadMessages() {
        messageHandler = new MessageHandler(this);
    }
    @SneakyThrows
    private void createBot() {
        getLogger().info(botSetting.getToken());
        jda = new BotBuilder(botSetting.getToken())
                .activity(botSetting.getActivity())
                .configureMemoryUsage().build();

        jda.awaitReady();

        getLogger().info("Successfully created discord bot...");

    }
}
