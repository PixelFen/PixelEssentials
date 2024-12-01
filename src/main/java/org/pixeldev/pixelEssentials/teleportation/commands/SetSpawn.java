package org.pixeldev.pixelEssentials.teleportation.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.pixeldev.pixelEssentials.teleportation.GetData;

public class SetSpawn implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        // Setting our server spawn
        Player player = (Player) commandSender;
        if (commandSender.hasPermission("pes.setspawn")) {
            GetData.x = player.getLocation().getX();
            GetData.y = player.getLocation().getY();
            GetData.z = player.getLocation().getZ();
            GetData.world = player.getLocation().getWorld().getName();
            commandSender.sendMessage("&a&l(✔) &r&aSpawn has been updated.");
            GetData.saveTPData();
        }


        return true;
    }
}
