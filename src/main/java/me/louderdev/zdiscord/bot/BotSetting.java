package me.louderdev.zdiscord.bot;

import lombok.Data;

@Data
public class BotSetting {

    private String token, activity;
    private long guildId, statusId;

}
