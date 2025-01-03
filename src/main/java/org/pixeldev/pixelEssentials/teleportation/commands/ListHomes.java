package org.pixeldev.pixelEssentials.teleportation.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.pixeldev.pixelEssentials.PixelEssentials;
import org.pixeldev.pixelEssentials.utils.Colorize;
import org.pixeldev.pixelEssentials.utils.TPData;

import java.util.Map;

public class ListHomes implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        PixelEssentials pi = PixelEssentials.getInstance();
        if (pi.getConfig().getBoolean("modules.teleportation")) {
            if (sender.hasPermission("pixelessentials.teleportation.list")) {
                Player player = (Player) sender;
                Object homelist = TPData.HomeList(player.getUniqueId());

                if (homelist == null) {
                    player.sendMessage(Colorize.colorize("&e&l[!] &r&cHey! You have no homes! Use &6/sethome &cto set one!"));
                }


            }
        }
        return true;
    }
}
