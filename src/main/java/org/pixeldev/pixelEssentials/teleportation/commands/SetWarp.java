package org.pixeldev.pixelEssentials.teleportation.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.pixeldev.pixelEssentials.PixelEssentials;
import org.pixeldev.pixelEssentials.teleportation.GetData;
import org.pixeldev.pixelEssentials.utils.Colorize;

public class SetWarp implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        PixelEssentials pi = PixelEssentials.getInstance();
        // Checking if the teleportation module is active

        if (pi.getConfig().getBoolean("modules.teleportation")) {
            if (commandSender.hasPermission("pes.setwarp")) {
                Player player = (Player) commandSender;

                // Coordinates of the warp and adding it to the warps hashmap
                double x = player.getLocation().getX();
                double y = player.getLocation().getY();
                double z = player.getLocation().getZ();
                String world = player.getWorld().getName();
                GetData.serverWarps.put(args[0], new GetData.WarpData(args[0], world, x, y, z));

                GetData.saveTPData();
                player.sendMessage(Colorize.colorize("&a&l(✔) &r&aWarp" + args[0] + "has been set."));
            }
        }
        return true;
    }
}
