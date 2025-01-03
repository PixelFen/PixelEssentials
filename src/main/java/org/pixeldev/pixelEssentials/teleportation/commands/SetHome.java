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
import org.pixeldev.pixelEssentials.utils.TPData;

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
                    // Checking if args[1] exists and that it's "overwrite"
                    if (args.length > 1) {
                        if (args[1].equalsIgnoreCase("overwrite")) {
                            // Overwriting home
                            boolean result = TPData.saveHome(
                                    uuid,
                                    args[0],
                                    player.getLocation().getX(),
                                    player.getLocation().getY(),
                                    player.getLocation().getZ(),
                                    player.getWorld().getName(),
                                    player.getLocation().getYaw(),
                                    player.getLocation().getPitch()
                            );
                            if (!result) {
                                player.sendMessage(Colorize.colorize("&c&l(!) &r&cSave failure. Please contact an admin."));
                                return true;
                            }
                            player.sendMessage(Colorize.colorize("&a&l(✔) &r&aHome " + args[0] + " has been overwritten."));
                        } else {
                            player.sendMessage(Colorize.colorize("&e&l[!] &r&bHome " + args[0] + " already exists! Use &6/sethome " + args[0] + " overwrite &bto overwrite it."));
                        }
                    }


                } if (!playerdata.contains(uuid.toString() + ".homes." + args[0])) {
                    // Adding home to playerdata.yml
                    // If I'm being honest, idk wtf I'm doing.

                    boolean result =TPData.saveHome(
                            uuid,
                            args[0],
                            player.getLocation().getX(),
                            player.getLocation().getY(),
                            player.getLocation().getZ(),
                            player.getWorld().getName(),
                            player.getLocation().getYaw(),
                            player.getLocation().getPitch()
                    );

                    if (!result) {
                        player.sendMessage(Colorize.colorize("&c&l(!) &r&cSave failure. Please contact an admin."));
                        return true;
                    }
                    player.sendMessage(Colorize.colorize("&a&l(✔) &r&aHome " + args[0] + " has been set to your location."));
                }
            } else {
                commandSender.sendMessage(Colorize.colorize("&c&l(!) &r&cYou do not have permission to use this command!"));
            }
        }
        return true;
    }
}


