package org.pixeldev.pixelEssentials.teleportation.commands;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.pixeldev.pixelEssentials.teleportation.GetData;
import org.pixeldev.pixelEssentials.utils.Colorize;

public class Spawn implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (commandSender.hasPermission("pes.spawn")) {
            // Teleporting the player to whatever is set in teleportation.GetData
            Player player = (Player) commandSender;
            double x = GetData.x;
            double y = GetData.y;
            double z = GetData.z;
            String world = GetData.world;
            // Getting the world based off of the world string
            World worldy = player.getServer().getWorld(world);
            Location loc = new Location(worldy, x, y, z);

            player.teleport(loc);
            player.sendMessage(Colorize.colorize("&aYou have been teleported to spawn."));
        }
        return true;
    }

}
