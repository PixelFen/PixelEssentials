package org.pixeldev.pixelEssentials.betterwhitelist.commands;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.pixeldev.pixelEssentials.PixelEssentials;
import org.pixeldev.pixelEssentials.betterwhitelist.Whitelister;
import org.pixeldev.pixelEssentials.utils.Colorize;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class WhitelistCommand implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        PixelEssentials pe = PixelEssentials.getInstance();

        // Sending the args for debugging purposes
        // Format: arg# : arg


        if (!pe.getConfig().getBoolean("modules.better-whitelist")) {
            sender.sendMessage(Colorize.colorize("&c&l(!) &r&cThe whitelist module is disabled in the config.yml!"));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(Colorize.colorize("&c&l(!) &r&cThis command has arguments! &7Usage:"));
            sender.sendMessage(Colorize.colorize("&7 &eWhitelist players: /whitelist <add|remove|list> <player>"));
            sender.sendMessage(Colorize.colorize("&7 &eWhitelist toggle: /whitelist <on|off>"));
        }

        else if (args[0].toLowerCase().matches("off")) {
            if (!sender.hasPermission("pes.whitelist.toggle")) {
                sender.sendMessage(Colorize.colorize("&c&l(!) &r&cYou do not have permission to use this command!"));
            }

            // Checking if Whitelister.whitelistEnabled is true.
            if (!Whitelister.whitelistEnabled) {
                sender.sendMessage(Colorize.colorize("&c&l(!) &r&cThe whitelist is already disabled!"));
                return true;
            }
            Whitelister.whitelistEnabled = false;

            sender.sendMessage(Colorize.colorize("&a&l(✔) &r&aThe whitelist has been &c&ldisabled&r&a."));

        }

        else if (args[0].toLowerCase().matches("on")) {
            // Checking if Whitelister.whitelistEnabled is false.
            if (Whitelister.whitelistEnabled) {
                sender.sendMessage(Colorize.colorize("&c&l(!) &r&cThe whitelist is already enabled!"));
                return true;
            }
            Whitelister.whitelistEnabled = true;

            sender.sendMessage(Colorize.colorize("&a&l(✔) &r&aThe whitelist has been &lenabled&r&a."));

        }

        // Whitelist add
        else if (args[0].toLowerCase().matches("add")) {
            // Adding user to our whitelist hashmap

            if (args.length < 2) {
                sender.sendMessage(Colorize.colorize("&c&l(!) &r&cYou need to specify a player! &7Usage:"));
                sender.sendMessage(Colorize.colorize("&7 &eWhitelist add: /whitelist add <player>"));
                return true;
            }

            // Creating an initialized player variable
            OfflinePlayer player;

            // Checking if the string is an IP address
            if (args[1].matches("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}")) {
                if (Whitelister.wlIPs.containsKey(args[1])) {
                    sender.sendMessage(Colorize.colorize("&c&l(!) &r&cThat IP is already whitelisted!"));
                    return true;
                }
                Whitelister.wlIPs.put(args[1], new Whitelister.wlIPs(args[1]));
                sender.sendMessage(Colorize.colorize("&a&l(✔) &r&aYou have whitelisted IP " + args[1] + "."));
                return true;
            }


            // Checking if the string is a UUID or not
            if (args[1].matches("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}")) {
                // Get user from UUID (getting offline user just in case they off)
                player = pe.getServer().getOfflinePlayer(UUID.fromString(args[1]));


            } else {
                // User is by name, so we have to get the uuid manually
                player = pe.getServer().getOfflinePlayer(args[1]);
            }

            // Checking if they are already whitelisted
            if (Whitelister.wlUUIDs.containsKey(player.getName())) {
                sender.sendMessage(Colorize.colorize("&c&l(!) &r&cThat player is already whitelisted!"));
                return true;
            }
            Whitelister.wlUUIDs.put(player.getName(), new Whitelister.UUIDWhitelist(player.getName(), player.getUniqueId().toString()));
            sender.sendMessage(Colorize.colorize("&a&l(✔) &r&a" + player.getName() + " (" + player.getUniqueId().toString() + ") was added to the whitelist!"));

            return true;

        }


        // Whitelist remove
        else if (args[0].toLowerCase().matches("remove")) {
            // Removing user from our whitelist hashmap

            if (args.length < 2) {
                sender.sendMessage(Colorize.colorize("&c&l(!) &r&cYou need to specify a player! &7Usage:"));
                sender.sendMessage(Colorize.colorize("&7 &eWhitelist remove: /whitelist remove <player>"));
                return true;
            }

            // Checking if the string is an IP address
            if (args[1].matches("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}")) {
                if (!Whitelister.wlIPs.containsKey(args[1])) {
                    sender.sendMessage(Colorize.colorize("&c&l(!) &r&cThat IP isn't whitelisted!"));
                    return true;
                }
                Whitelister.wlIPs.remove(args[1]);
                sender.sendMessage(Colorize.colorize("&a&l(✔) &r&aYou have removed the IP " + args[1] + " from the whitelist."));
                return true;
            }

            // Creating an initialized player variable
            OfflinePlayer player;

            // Checking if the string is a UUID or not
            if (args[1].matches("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}")) {
                // Get user from UUID (getting offline user just in case they off)
                player = pe.getServer().getOfflinePlayer(UUID.fromString(args[1]));
            } else {
                // User is by name, so we have to get the uuid manually
                player = pe.getServer().getOfflinePlayer(args[1]);
            }

            // Checking if they are already whitelisted
            if (!Whitelister.wlUUIDs.containsKey(player.getName())) {
                sender.sendMessage(Colorize.colorize("&c&l(!) &r&cThat player is not whitelisted!"));
                return true;
            }

            Whitelister.wlUUIDs.remove(player.getName());
            sender.sendMessage(Colorize.colorize("&a&l(✔) &r&aYou have removed " + player.getName() + " (" + player.getUniqueId().toString() + ") from the whitelist."));
            return true;
        }







        else if (args[0].toLowerCase().matches("list")) {


            Boolean wl = Whitelister.whitelistEnabled;
            String wlValue;

            if (wl) {
                wlValue = "Enabled";
            } else {
                wlValue = "Disabled";
            }
            sender.sendMessage(Colorize.colorize("&eWhitelist is currently " + wlValue + "."));
            sender.sendMessage(Colorize.colorize("&eUsers whitelisted: "));

            // Looping our hashmap then formatting as player (UUID)
            // Format: player (UUID)
            for (Map.Entry<String, Whitelister.UUIDWhitelist> entry : Whitelister.wlUUIDs.entrySet()) {
                sender.sendMessage(Colorize.colorize("&7 - " + entry.getKey() + " (" + entry.getValue().uuid + ")"));
            }


            sender.sendMessage("");

            if (sender.hasPermission("pes.whitelist.ips")) {
                // Doing the same for our IPs

                sender.sendMessage(Colorize.colorize("&eIPs whitelisted: "));

                for (Map.Entry<String, Whitelister.wlIPs> entry : Whitelister.wlIPs.entrySet()) {
                    sender.sendMessage(Colorize.colorize("&7 - " + entry.getKey()));
                }

            }

        }

        return true;
    }
}
