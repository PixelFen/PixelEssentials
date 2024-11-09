package org.pixeldev.pixelEssentials.commands.etc;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.pixeldev.pixelEssentials.utils.Colorize;

public class Fly implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if (sender.hasPermission("pes.fly")) {
            if (player.getAllowFlight()) {
                player.setAllowFlight(false);
                player.sendMessage(Colorize.colorize("&a&l(✔) &r&aFlight has been &c&ldisabled&a."));
            } else {
                player.setAllowFlight(true);
                player.sendMessage(Colorize.colorize("&a&l(✔) &r&aFlight has been &lenabled&r&a,"));
            }
        }
        return true;
    }
}
