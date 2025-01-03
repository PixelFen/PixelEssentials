package org.pixeldev.pixelEssentials.discord.commands.ingame;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.pixeldev.pixelEssentials.discord.Bot;

public class discord implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Checking if there are no args

        if (args.length == 0) {
            // Checking if the Discord bot is connected. If so, tell the user that it is connected.
            // If not, tell the user that it is not connected.

            Bot bot = new Bot();
            if (bot.GetBot() == null) {
                sender.sendMessage("The Discord bot is not connected.");
            } else {
                sender.sendMessage("The Discord bot is connected.");
            }
        } else if (args[0].toLowerCase().matches("status")) {
            // Checking if the Discord bot is connected. If so, tell the user that it is connected.
            // If not, tell the user that it is not connected.

            Bot bot = new Bot();
            if (bot.GetBot() == null) {
                sender.sendMessage("The Discord bot is not connected.");
            } else {
                sender.sendMessage("The Discord bot is connected.");
            }
        } else if (args[0].toLowerCase().matches("connect")) {
            // Checking if the Discord bot is connected. If so, tell the user that it is connected.
            // If not, tell the user that it is not connected.

            Bot bot = new Bot();
            bot.connectDiscord();
            sender.sendMessage("Connecting to Discord");

        }
        return true;
    }
}