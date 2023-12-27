package me.louderdev.zdiscord;

import lombok.Getter;
import lombok.SneakyThrows;
import me.louderdev.zdiscord.api.ZDiscordApi;
import me.louderdev.zdiscord.bot.BotBuilder;
import me.louderdev.zdiscord.bot.BotSetting;
import me.louderdev.zdiscord.messages.MessageHandler;
import me.louderdev.zdiscord.utils.file.ConfigFile;
import net.dv8tion.jda.api.JDA;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class ZDiscord extends JavaPlugin {

    @Getter private static ZDiscord instance;

    private JDA jda;
    private BotSetting botSetting;
    private MessageHandler messageHandler;
    private ConfigFile configFile;

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
        configFile = new ConfigFile(this, "config.yml");

        botSetting = new BotSetting();
        botSetting.setToken(configFile.getString("BOT.TOKEN"));
        botSetting.setActivity(configFile.getString("BOT.ACTIVITY"));
        botSetting.setGuildId(configFile.getLong("GUILD_ID"));
        botSetting.setStatusId(configFile.getLong("CHANNEL_ID.STATUS_LOGGER"));

        getLogger().info("Loaded Bot settings...");
    }

    private void loadMessages() {
        messageHandler = new MessageHandler(this);

        getLogger().info("Loaded handlers...");
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
