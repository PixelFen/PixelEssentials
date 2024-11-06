package org.pixeldev.pixelEssentials.commands.serverAdmin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.pixeldev.pixelEssentials.PixelEssentials;
import org.pixeldev.pixelEssentials.utils.Colorize;

public class ReloadConfigs implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        // TODO: check if an argument for which config to reload.

        if (commandSender.hasPermission("pes.reload")) {
            // For now, we are gonna reload the default config.
            PixelEssentials instance = PixelEssentials.getInstance();
            instance.reloadConfig();
            instance.loadConfigs();
            commandSender.sendMessage("Config reloaded.");
        } else {
            commandSender.sendMessage(Colorize.colorize("<red>You require the</red> <gold>pes.reload</gold> <red>permission to use this command!"));
        }
        return true;


    }


}
