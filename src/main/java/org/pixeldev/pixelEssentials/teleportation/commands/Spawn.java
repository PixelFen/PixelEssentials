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
import org.pixeldev.pixelEssentials.utils.TPData;

import java.util.Map;

public class Spawn implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (commandSender.hasPermission("pes.spawn")) {
            // Teleporting the player to whatever is set in teleportation.GetData
            Player player = (Player) commandSender;

            Map<String, Object> spawn = TPData.GetSpawn();

            assert spawn != null;
            double x = (double) spawn.get("x");
            double y = (double) spawn.get("y");
            double z = (double) spawn.get("z");
            String world = (String) spawn.get("world");
            float yaw = (float) spawn.get("yaw");
            float pitch = (float) spawn.get("pitch");
            // Getting the world based off of the world string
            World worldy = player.getServer().getWorld(world);
            Location loc = new Location(worldy, x, y, z, yaw, pitch);

            player.teleport(loc);
            player.sendMessage(Colorize.colorize("&aYou have been teleported to spawn."));
        }
        return true;
    }

}
