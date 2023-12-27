package me.louderdev.zdiscord.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public class BotBuilder {

    private JDABuilder builder;

    public BotBuilder(String token) {
        this.builder = JDABuilder.createLight(token);
    }

    public BotBuilder activity(String activity) {
        builder.setActivity(Activity.watching(activity));
        return this;
    }
    public BotBuilder configureMemoryUsage() {
        // Disable cache for member activities (streaming/games/spotify)
        builder.disableCache(CacheFlag.ACTIVITY);

        // Cached no one
        builder.setMemberCachePolicy(MemberCachePolicy.NONE);

        // Disable member chunking on startup
        builder.setChunkingFilter(ChunkingFilter.NONE);

        // Disable presence updates and typing events
        builder.disableIntents(GatewayIntent.GUILD_PRESENCES, GatewayIntent.GUILD_MESSAGE_TYPING);

        // Consider guilds with more than 50 members as "large".
        // Large guilds will only provide online members in their setup and thus reduce bandwidth if chunking is disabled.
        builder.setLargeThreshold(50);

        return this;
    }

    public JDA build() {
        return builder.build();
    }

}
