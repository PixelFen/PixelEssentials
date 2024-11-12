package org.pixeldev.pixelEssentials.betterwhitelist.events;

import com.google.gson.Gson;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.pixeldev.pixelEssentials.PixelEssentials;
import org.pixeldev.pixelEssentials.betterwhitelist.Whitelister;
import org.pixeldev.pixelEssentials.utils.Colorize;

import java.io.*;
import java.util.Map;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class PreMemberJoin implements Listener {
    PixelEssentials pe = PixelEssentials.getInstance();

    @EventHandler
    public void PlayerPreLogin(AsyncPlayerPreLoginEvent e) {
        // Players IP
        String playerIP = e.getAddress().getHostAddress();
        // Player's UUID
        String playerUUID = e.getUniqueId().toString();
        // Player's name
        String playerName = e.getName();

        // Checking if the player is in whitelist
        if (pe.getConfig().getBoolean("modules.better-whitelist")) {

            if (Whitelister.whitelistEnabled) {
                // Checking if the player's UUID is in the whitelist hashmap
                boolean isWhitelisted = Whitelister.wlUUIDs.values().stream()
                        .anyMatch(whitelistEntry -> whitelistEntry.uuid.equals(playerUUID));
                if (isWhitelisted) {
                    // Player is whitelisted
                    e.allow();
                    } else if (Whitelister.wlIPs.containsKey(playerIP)) {
                    // Player is whitelisted
                        e.allow();
                } else {
                    // Player is not whitelisted
                    e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, Colorize.colorize((String) Objects.requireNonNull(PixelEssentials.getInstance().getConfig().get("whitelist-message"))));
                }

            }
        }
    }
}




