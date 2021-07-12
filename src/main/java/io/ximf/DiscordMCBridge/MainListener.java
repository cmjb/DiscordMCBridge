package io.ximf.DiscordMCBridge;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.advancement.Advancement;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.*;

import javax.security.auth.login.LoginException;
import java.util.Formatter;
import java.util.logging.Logger;

public final class MainListener implements Listener {

    JDA jda = null;

    public MainListener(Main plugin, JDA jda) {
        this.jda = jda;
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {

    }

    @EventHandler
    public void PlayerAdvancementDoneEvent(PlayerAdvancementDoneEvent event) {
        Player player = (Player) event.getPlayer();
        Config config = new Config();
        String playerName = player.getDisplayName();
        Advancement advancement = event.getAdvancement();
        String advancementName = advancement.getClass().getSimpleName().replaceAll("_", "\\s+").toLowerCase();

        String message = "**" + playerName + "** " + " achieved the advancement **" + advancementName + "**!";

        TextChannel textchannel = jda.getTextChannelById(config.getDiscordChannel());

        textchannel.sendMessage("**["+ config.getMinecraftDiscordAnnounceName() +"]**: " + message).queue();

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Config config = new Config();
        String playerName = player.getDisplayName();
        String message = "**" + playerName + "** " + " has joined minecraft!";

        TextChannel textchannel = jda.getTextChannelById(config.getDiscordChannel());

        textchannel.sendMessage("**["+ config.getMinecraftDiscordAnnounceName() +"]**: " + message).queue();

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getDisplayName();
        String message = "**" + playerName + "** " + " has quit minecraft!";
        Config config = new Config();

        TextChannel textchannel = jda.getTextChannelById(config.getDiscordChannel());

        textchannel.sendMessage("**["+ config.getMinecraftDiscordAnnounceName() +"]**: " + message).queue();

    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getDisplayName();
        String content = event.getMessage();
        Config config = new Config();
        TextChannel textchannel = jda.getTextChannelById(config.getDiscordChannel());

        String message = playerName + ": " + content;

        textchannel.sendMessage("["+ config.getMinecraftDiscordAnnounceName() +"]: " + message).queue();

    }

    @EventHandler
    public void onEntityDeath (EntityDeathEvent event) {
        String deathBy;
        String triggerOption;

        if (event.getEntity() == null || !(event.getEntity() instanceof Player)) return;
        Config config = new Config();
        EntityDamageEvent damageEvent = event.getEntity().getLastDamageCause();
        EntityDamageEvent.DamageCause causeOfDeath = null;
        Player deadNerd = (Player) event.getEntity();

        if (damageEvent != null)  causeOfDeath = damageEvent.getCause();
        if (causeOfDeath == null) causeOfDeath = EntityDamageEvent.DamageCause.CUSTOM;
        triggerOption = causeOfDeath.toString().toLowerCase();

        if (causeOfDeath == EntityDamageEvent.DamageCause.ENTITY_ATTACK && damageEvent instanceof EntityDamageByEntityEvent && ((EntityDamageByEntityEvent) damageEvent).getDamager() instanceof Player) {
            Player killer = (Player) ((EntityDamageByEntityEvent) damageEvent).getDamager();
            String weapon = killer.getItemInHand().getType().toString().toLowerCase().replace("_", " ");
            if (weapon.equals("air")) weapon = "fists";

            String message = "**" + killer.getDisplayName() + "** killed **" + deadNerd.getDisplayName() + "** with " + weapon;
            TextChannel textchannel = jda.getTextChannelById(config.getDiscordChannel());

            textchannel.sendMessage("["+ config.getMinecraftDiscordAnnounceName() +"]: " + message).queue();

        } else {

            String message = "**" + deadNerd.getDisplayName() + "** died.";
            TextChannel textchannel = jda.getTextChannelById(config.getDiscordChannel());
            textchannel.sendMessage("["+ config.getMinecraftDiscordAnnounceName() +"]: " + message).queue();

        }
    }
}