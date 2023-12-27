package me.louderdev.zdiscord.messages;

import me.louderdev.zdiscord.ZDiscord;
import me.louderdev.zdiscord.utils.file.ConfigFile;

import java.util.ArrayList;
import java.util.List;

public class MessageHandler<T> {

    private final ZDiscord plugin;
    private final List<IMessage> messages = new ArrayList<>();

    public MessageHandler(ZDiscord plugin) {
        this.plugin = plugin;

        loadMessages();
    }

    private void loadMessages() {
        ConfigFile configFile = plugin.getC();

        messages.add(new StatusMessage().load(configFile));
        plugin.getLogger().info("Loaded " + messages.size() + " messages.");
    }

    public IMessage<T> getById(String id) {
        return messages.stream()
                .filter(m -> m.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }
}
