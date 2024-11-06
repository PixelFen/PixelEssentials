package org.pixeldev.pixelEssentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.pixeldev.pixelEssentials.PixelEssentials;
import org.pixeldev.pixelEssentials.commands.serverAdmin.VersionCmd;
import org.pixeldev.pixelEssentials.utils.Colorize;

public class MainCmds implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        // This is the base command. Will tie it in with other functions like reload so I don't have to have a
        // /reload command that may conflict with the main command.

        if (args.length == 0) {
            // There are no arguments, force running the /version command.
            VersionCmd versionCmd = new VersionCmd();
            versionCmd.onCommand(commandSender, command, s, args);
        }

        else if (args[0].toLowerCase().equals("config")) {
            if (args[1].toLowerCase().equals("modules")) {
                if (args[2].toLowerCase().equals("whitelist")) {
                    if (args[3].toLowerCase().equals("disable")) {
                        PixelEssentials pe = PixelEssentials.getInstance();
                        pe.getConfig().set("modules.better-whitelist", false);
                        pe.saveConfig();
                        pe.reloadConfig();
                        commandSender.sendMessage(Colorize.colorize("&a&l(✔) &r&aThe whitelist module has been disabled."));
                    }
                    else if (args[3].toLowerCase().equals("enable")) {
                        PixelEssentials pe = PixelEssentials.getInstance();
                        pe.getConfig().set("modules.better-whitelist", true);
                        pe.saveConfig();
                        pe.reloadConfig();
                        commandSender.sendMessage(Colorize.colorize("&a&l(✔) &r&aThe whitelist module has been enabled."));
                    }
                }
            }
        }

        return true;
    }
}
