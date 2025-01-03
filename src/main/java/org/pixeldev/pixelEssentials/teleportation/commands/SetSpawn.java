package org.pixeldev.pixelEssentials.teleportation.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.pixeldev.pixelEssentials.PixelEssentials;
import org.pixeldev.pixelEssentials.teleportation.GetData;
import org.pixeldev.pixelEssentials.utils.Colorize;
import org.pixeldev.pixelEssentials.utils.TPData;

public class SetSpawn implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        // Setting our server spawn
        Player player = (Player) commandSender;
        PixelEssentials pi = PixelEssentials.getInstance();
        if (commandSender.hasPermission("pes.setspawn")) {
            boolean result = TPData.saveSpawn(
                    player.getLocation().getX(),
                    player.getLocation().getY(),
                    player.getLocation().getZ(),
                    player.getLocation().getWorld().getName(),
                    player.getYaw(),
                    player.getPitch()
            );
            if (!result) {
                commandSender.sendMessage(Colorize.colorize("&c&l(!) &r&cSave failure. Please contact an admin."));
                return true;
            } else {
                commandSender.sendMessage(Colorize.colorize("&a&l(✔) &r&aSpawn has been updated."));
                GetData.saveTPData();
            }
        }


        return true;
    }
}
