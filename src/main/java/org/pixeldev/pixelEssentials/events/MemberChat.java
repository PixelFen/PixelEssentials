package org.pixeldev.pixelEssentials.events;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.pixeldev.pixelEssentials.PixelEssentials;
import org.pixeldev.pixelEssentials.discord.Bot;

import java.awt.*;

public class MemberChat implements Listener {
    @EventHandler
    public void onChat(AsyncChatEvent e) {
        PixelEssentials pi = PixelEssentials.getInstance();
        String channelString = pi.getConfig().get("discord.chattest").toString();
        long channelID = Long.parseLong(channelString);

        Bot bot = new Bot();
        String message = ((TextComponent) e.message()).content();


        bot.GetBot().getTextChannelById(channelID).sendMessage(e.getPlayer().getName() + ": " + message).queue();

    }
}
