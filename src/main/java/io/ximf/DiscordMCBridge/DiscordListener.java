package io.ximf.DiscordMCBridge;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.util.logging.Logger;

public class DiscordListener extends ListenerAdapter {

    private static final String token = "";
    private JDA jda = null;
    @SuppressWarnings("unused")
    public static String getToken()
    {
        return token;
    }

    public DiscordListener(JDA jda) {
        this.jda = jda;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Config config = new Config();
        MessageChannel channel = event.getChannel();
        Message message = event.getMessage();
        String content = message.getContentStripped();
        String name = event.getMember().getEffectiveName();

        if(channel.getIdLong() == config.getDiscordChannel() && name != null && name.equals(config.getDiscordBotName()) != true)
        {
            if(content.startsWith("!whitelist") && config.getSelfWhitelist()) {
                TextChannel textchannel = jda.getTextChannelById(config.getDiscordChannel());
                Whitelister whitelister = new Whitelister();
                try {
                    whitelister.whitelistPlayer(content.split(" ")[1]);
                    textchannel.sendMessage("Adding " + content.split(" ")[1]).queue();
                } catch (Exception exception) {
                    textchannel.sendMessage("Can't add due to an exception: " + exception.getMessage()).queue();
                }
            } else {
                Main.sendMessage("["+ config.getMinecraftDiscordAnnounceName() +"] " + name + ": " + content);
            }
        }
    }

}
