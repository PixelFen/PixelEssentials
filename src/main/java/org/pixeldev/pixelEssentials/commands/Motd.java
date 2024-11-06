package org.pixeldev.pixelEssentials.commands;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.pixeldev.pixelEssentials.PixelEssentials;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.pixeldev.pixelEssentials.utils.Colorize;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Motd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        PixelEssentials instance = PixelEssentials.getInstance();
        Player player = (Player) commandSender;

        try {
            File motdTxT = new File(instance.getDataFolder() + "\\motd.txt");
            Scanner glasses = new Scanner(motdTxT);
            commandSender.sendMessage(Colorize.colorize("&6-- Previewing motd.txt file --"));
            while (glasses.hasNextLine()) {
                String data = glasses.nextLine();

                String holderreplace = PlaceholderAPI.setPlaceholders(player, data);
                commandSender.sendMessage(Colorize.colorize(holderreplace));

            }
            commandSender.sendMessage(Colorize.colorize("&6-----------------------------"));
        } catch (FileNotFoundException e) {
            commandSender.sendMessage("No MOTD exists. Creating one for you...");
            try {
                File newBoi = new File(instance.getDataFolder() + "\\motd.txt");
                newBoi.createNewFile();
                FileWriter pencil = new FileWriter(instance.getDataFolder() + "\\motd.txt");

                pencil.write("Welcome to the server, %player_name%!");
                pencil.close();
            } catch (IOException ex) {
                instance.getLogger().warning("Unable to create a new motd.txt!");
                e.printStackTrace();
            }
        }

        return true;
    }
}
