package org.pixeldev.pixelEssentials.events;

import net.dv8tion.jda.api.EmbedBuilder;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.pixeldev.pixelEssentials.PixelEssentials;
import org.pixeldev.pixelEssentials.discord.Bot;

import java.awt.*;

public class MemberLeave implements Listener {
    PixelEssentials pi = PixelEssentials.getInstance();

    @EventHandler
    public void memberLeaveEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        FileConfiguration config = pi.getConfig();

        pi.getLogger().info("Player join event happened (test)");

        // TODO: Check if the player is banned.
        // Pass to a prejoin event

        // Discord bot embed
        Bot bot = new Bot();
        EmbedBuilder embed = new EmbedBuilder();

        //
        embed.setAuthor(player.getName() + " left the server", null, "https://api.mineatar.io/face/" + player.getUniqueId());
        embed.setColor(Color.RED);

        String channelString = pi.getConfig().get("discord.chattest").toString();
        long channelID = Long.parseLong(channelString);
        bot.GetBot().getTextChannelById(channelID).sendMessageEmbeds(embed.build()).queue();
    }
}
