package org.pixeldev.pixelEssentials.events;

import me.clip.placeholderapi.PlaceholderAPI;
import net.dv8tion.jda.api.EmbedBuilder;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitScheduler;
import org.pixeldev.pixelEssentials.PixelEssentials;
import org.pixeldev.pixelEssentials.discord.Bot;
import org.pixeldev.pixelEssentials.utils.Colorize;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

public class MemberJoin implements Listener {
    PixelEssentials pi = PixelEssentials.getInstance();

    public void sendMotd(Player player) {
        FileConfiguration config = pi.getConfig();
        if (Objects.requireNonNull(config.getString("sendmotd").toLowerCase()).matches("true")) {

            try {
                File motdTxT = new File(pi.getDataFolder(), "motd.txt");
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

        FileConfiguration config = pi.getConfig();

        pi.getLogger().info("Player join event happened (test)");

        // TODO: Check if the player is banned.
        // Pass to a prejoin event

        // Discord bot embed
        Bot bot = new Bot();
        EmbedBuilder embed = new EmbedBuilder();

        //
        embed.setAuthor(player.getName() + " joined the server", null, "https://api.mineatar.io/face/" + player.getUniqueId());
        embed.setColor(Color.GREEN);

        String channelString = pi.getConfig().get("discord.chattest").toString();
        long channelID = Long.parseLong(channelString);
        bot.GetBot().getTextChannelById(channelID).sendMessageEmbeds(embed.build()).queue();




        // MOTD thingy
        BukkitScheduler sch = Bukkit.getScheduler();
        sch.scheduleSyncDelayedTask(pi, () -> {
            sendMotd(player);
        }, config.getInt("motddelay"));

    }
}
