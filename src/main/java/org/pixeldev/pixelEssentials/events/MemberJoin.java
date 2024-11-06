package org.pixeldev.pixelEssentials.events;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitScheduler;
import org.pixeldev.pixelEssentials.PixelEssentials;
import org.pixeldev.pixelEssentials.utils.Colorize;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

public class MemberJoin implements Listener {
    PixelEssentials instance = PixelEssentials.getInstance();

    public void sendMotd(Player player) {
        FileConfiguration config = instance.getConfig();
        if (Objects.requireNonNull(config.getString("sendmotd")).matches("true")) {

            try {
                File motdTxT = new File(instance.getDataFolder() + "\\motd.txt");
                Scanner glasses = new Scanner(motdTxT);
                // Adding a second delay before the message


                while (glasses.hasNextLine()) {
                    String data = glasses.nextLine();
                    String holderreplace = PlaceholderAPI.setPlaceholders(player, data);
                    player.sendMessage(Colorize.colorize(holderreplace));
                }
            } catch (FileNotFoundException e) {
                player.sendMessage("Welcome to the server, %player_name%!");
            }
        }
    }

    @EventHandler
    public void memberJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        FileConfiguration config = instance.getConfig();

        instance.getLogger().info("Player join event happened (test)");

        // TODO: Check if the player is banned.

        // MOTD thingy
        BukkitScheduler sch = Bukkit.getScheduler();
        sch.scheduleSyncDelayedTask(instance, () -> {
            sendMotd(player);
        }, config.getInt("motddelay"));

    }
}
