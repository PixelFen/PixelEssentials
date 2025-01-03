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
import org.pixeldev.pixelEssentials.utils.TPData;

import java.util.Map;

public class GoHome implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        PixelEssentials pi = PixelEssentials.getInstance();
        if (pi.getConfig().getBoolean("modules.teleportation")) {
            if (commandSender.hasPermission("pes.home")) {
                Player player = (Player) commandSender;

                // Making sure the home exists.

                Map<String, Object> homes = TPData.GetHome(player.getUniqueId(), args[0]);

                if (homes == null) {
                    player.sendMessage(Colorize.colorize("&e&l[!] &r&cHey! This home doesn't exist. Use &6/home list &cto see all homes!"));
                    return true;
                }

                // Teleporting the player to the home.
                double x = (double) homes.get("x");
                double y = (double) homes.get("y");
                double z = (double) homes.get("z");
                String world = (String) homes.get("world");
                float yaw = (float) homes.get("yaw");
                float pitch = (float) homes.get("pitch");
                World worldy = player.getServer().getWorld(world);

                Location loc = new Location(worldy, x, y, z, yaw, pitch);
                player.teleport(loc);
                player.sendMessage(Colorize.colorize("&a&l(✔) &r&aTeleported to &6" + args[0] + "&r&a."));




            }
        }
        return true;
    }
}
