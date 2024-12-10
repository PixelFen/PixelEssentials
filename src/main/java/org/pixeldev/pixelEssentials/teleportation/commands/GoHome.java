package org.pixeldev.pixelEssentials.teleportation.commands;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.pixeldev.pixelEssentials.PixelEssentials;
import org.pixeldev.pixelEssentials.utils.Colorize;

public class GoHome implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        PixelEssentials pi = PixelEssentials.getInstance();
        if (pi.getConfig().getBoolean("modules.teleportation")) {
            if (commandSender.hasPermission("pes.home")) {
                Player player = (Player) commandSender;

                // Making sure the home exists.
                FileConfiguration playerdata = pi.PlayerData();
                if (!playerdata.contains(player.getUniqueId().toString() + ".homes." + args[0])) {
                    player.sendMessage(Colorize.colorize("&e&l[!] &r&cHey! This home doesn't exist. Use &6/home list &cto see all homes!"));
                    return true;
                }

                // Teleporting the player to the home.
                double x = playerdata.getDouble(player.getUniqueId().toString() + ".homes." + args[0] + ".x");
                double y = playerdata.getDouble(player.getUniqueId().toString() + ".homes." + args[0] + ".y");
                double z = playerdata.getDouble(player.getUniqueId().toString() + ".homes." + args[0] + ".z");
                String world = playerdata.getString(player.getUniqueId().toString() + ".homes." + args[0] + ".world");
                float yaw = (float) playerdata.getDouble(player.getUniqueId().toString() + ".homes." + args[0] + ".yaw");
                float pitch = (float) playerdata.getDouble(player.getUniqueId().toString() + ".homes." + args[0] + ".pitch");
                World worldy = player.getServer().getWorld(world);
                Location loc = new Location(worldy, x, y, z, yaw, pitch);
                player.teleport(loc);
                player.sendMessage(Colorize.colorize("&a&l(✔) &r&aWelcome home to &6" + args[0] + "&r&a."));




            }
        }
        return true;
    }
}
