package io.ximf.DiscordMCBridge;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class Main extends JavaPlugin {

    JDA jda = null;
    DiscordListener discordListener = null;

    @Override
    public void onEnable() {
        Config config = new Config();
        Logger log = Bukkit.getLogger();

        try {
            jda = JDABuilder.createDefault(config.getDiscordToken()).build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
        discordListener = new DiscordListener();
        jda.addEventListener(discordListener);
        getServer().getPluginManager().registerEvents(new MainListener(this, jda), this);
    }

    @Override
    public void onDisable() {
        if(jda != null)
        {
            jda.removeEventListener(discordListener);
        }
        getServer().getPluginManager().disablePlugin(this);
    }

    public static int sendMessage(String message) {
        return Bukkit.getServer().broadcastMessage(message);
    }


}