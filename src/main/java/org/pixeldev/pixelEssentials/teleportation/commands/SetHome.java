package org.pixeldev.pixelEssentials.teleportation.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.pixeldev.pixelEssentials.PixelEssentials;
import org.pixeldev.pixelEssentials.teleportation.data.PlayerHomes;
import org.pixeldev.pixelEssentials.utils.Colorize;

import java.util.UUID;

public class SetHome implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        PixelEssentials pi = PixelEssentials.getInstance();

        if (pi.getConfig().getBoolean("modules.teleportation")) {
            if (commandSender.hasPermission("pes.sethome")) {
                Player player = (Player) commandSender;

                // Checking if home already exists
                FileConfiguration playerdata = pi.PlayerData();
                UUID uuid = player.getUniqueId();
                if (playerdata.contains(uuid.toString() + ".homes." + args[0])) {
                    if (!args[1].toLowerCase().equals("overwrite")) {
                        player.sendMessage(Colorize.colorize("&e&l[!] &r&cHey! This home exists already. Use &6/home " + args[0] + " overwrite &cto teleport there!"));
                        return true;
                    }

                } if (!playerdata.contains(uuid.toString() + ".homes." + args[0]) || args[1].toLowerCase().equals("overwrite")) {
                    // Adding home to playerdata.yml
                    playerdata.set(uuid.toString() + ".homes." + args[0] + ".world", player.getWorld().getName());
                    playerdata.set(uuid.toString() + ".homes." + args[0] + ".x", player.getLocation().getX());
                    playerdata.set(uuid.toString() + ".homes." + args[0] + ".y", player.getLocation().getY());
                    playerdata.set(uuid.toString() + ".homes." + args[0] + ".z", player.getLocation().getZ());
                    playerdata.set(uuid.toString() + ".homes." + args[0] + ".yaw", player.getLocation().getYaw());
                    playerdata.set(uuid.toString() + ".homes." + args[0] + ".pitch", player.getLocation().getPitch());

                    // If I'm being honest, idk wtf I'm doing.

                    pi.savePlayerData();

                    player.sendMessage(Colorize.colorize("&a&l(✔) &r&aHome " + args[0] + " has been set to your location."));
                }
            } else {
                commandSender.sendMessage(Colorize.colorize("&c&l(!) &r&cYou do not have permission to use this command!"));
            }
        }
        return true;
    }
}


