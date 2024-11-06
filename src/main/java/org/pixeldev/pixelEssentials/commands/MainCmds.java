package org.pixeldev.pixelEssentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.pixeldev.pixelEssentials.commands.serverAdmin.VersionCmd;

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

        return true;
    }
}
