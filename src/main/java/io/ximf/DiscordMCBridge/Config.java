package io.ximf.DiscordMCBridge;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Config {
    private FileConfiguration getConfigYaml() {
        File file = new File("plugins/DiscordMCBridge/settings.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        return config;
    }

    public Long getDiscordChannel() {
        FileConfiguration config = this.getConfigYaml();
        return config.getLong("discord_channel", 0L);
    }

    public String getDiscordToken() {
        FileConfiguration config = this.getConfigYaml();
        return config.getString("discord_token", "");
    }

    public String getDiscordBotName() {
        FileConfiguration config = this.getConfigYaml();
        return config.getString("discord_bot_name", "");
    }

    public String getMinecraftDiscordAnnounceName() {
        FileConfiguration config = this.getConfigYaml();
        return config.getString("minecraft_discord_announce_name", "");
    }
}
