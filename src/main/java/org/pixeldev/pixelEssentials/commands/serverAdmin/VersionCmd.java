package org.pixeldev.pixelEssentials.commands.serverAdmin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.pixeldev.pixelEssentials.PixelEssentials;
import org.pixeldev.pixelEssentials.utils.Colorize;

public class VersionCmd implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        String versionStr = Colorize.colorize("&6You are currently running version: &c" + PixelEssentials.getInstance().getDescription().getVersion() + " &6of PixelEssesntials");
        String vertwo = Colorize.colorize("&6Developed with &c♥ &6by PixelFen");
        commandSender.sendMessage(versionStr);
        commandSender.sendMessage(vertwo);
        return true;
    }
}
