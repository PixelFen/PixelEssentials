package org.pixeldev.pixelEssentials.teleportation.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.pixeldev.pixelEssentials.PixelEssentials;
import org.pixeldev.pixelEssentials.utils.Colorize;

public class DelHome implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        PixelEssentials pi = PixelEssentials.getInstance();

        if (pi.getConfig().getBoolean("modules.teleportation")) {
            if (sender.hasPermission("pes.delhome")) {
                Player player = (Player) sender;
                FileConfiguration playerdata = pi.PlayerData();
                playerdata.set(player.getUniqueId().toString() + ".homes." + args[0], null);
                pi.savePlayerData();
                player.sendMessage(Colorize.colorize("&a&l(✔) &r&aHome " + args[0] + " has been removed."));
            }
        }
        return true;
    }
}
