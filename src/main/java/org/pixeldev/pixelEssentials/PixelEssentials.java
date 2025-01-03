package org.pixeldev.pixelEssentials;

import org.bukkit.configuration.file.YamlConfiguration;
import org.pixeldev.pixelEssentials.betterwhitelist.Whitelister;
import org.pixeldev.pixelEssentials.betterwhitelist.commands.WhitelistCommand;
import org.pixeldev.pixelEssentials.betterwhitelist.events.PreMemberJoin;
import org.pixeldev.pixelEssentials.commands.MainCmds;
import org.pixeldev.pixelEssentials.commands.etc.Fly;
import org.pixeldev.pixelEssentials.commands.serverAdmin.ReloadConfigs;
import org.pixeldev.pixelEssentials.commands.serverAdmin.VersionCmd;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.pixeldev.pixelEssentials.commands.Motd;
import org.pixeldev.pixelEssentials.discord.Bot;
import org.pixeldev.pixelEssentials.discord.commands.ingame.discord;
import org.pixeldev.pixelEssentials.events.MemberChat;
import org.pixeldev.pixelEssentials.events.MemberJoin;
import org.pixeldev.pixelEssentials.events.MemberLeave;
import org.pixeldev.pixelEssentials.teleportation.commands.*;

import java.io.File;
import java.io.IOException;

import static org.pixeldev.pixelEssentials.utils.ConfigValidation.updateConfig;

// If I'm being honest, I have no idea what the fuck I'm doing.

public final class PixelEssentials extends JavaPlugin {
    private static PixelEssentials instance;

    public static PixelEssentials getInstance() {
        return instance;
    }




    public int getConfigVersions() {
        return 1;
    }


    public void loadConfigs(){
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();

    }

    // System to autosave whitelist every 30 minutes
    public void autoSaveWhitelist() throws IOException {
        Whitelister.saveWhitelist();

    }

    // { Player data storage } \\
    private File playerData;
    private FileConfiguration playerDataYml;
    private void createPlayerData() {
        playerData = new File(getDataFolder() + "/data", "playerdata.yml");
        if (!playerData.getParentFile().exists()) {
            playerData.getParentFile().mkdirs();
        }
        playerDataYml = YamlConfiguration.loadConfiguration(playerData);
    }
    // Saving playerdata.yml
    public void savePlayerData() {
        try {
            playerDataYml.save(playerData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public FileConfiguration PlayerData() {
        return this.playerDataYml;
    }


    // { Server data storage } \\
    private File serverDataFile;
    private FileConfiguration serverData;

    private void createServerData() {
        serverDataFile = new File(getDataFolder() + "/data", "serverdata.yml");
        if (!serverDataFile.getParentFile().exists()) {
            serverDataFile.getParentFile().mkdirs();
        }
        serverData = YamlConfiguration.loadConfiguration(serverDataFile);
    }

    public void saveServerData() {
        try {
            serverData.save(serverDataFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public FileConfiguration GetServerData() {
        return this.serverData;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        loadConfigs();
        FileConfiguration config = getConfig();

        createPlayerData();
        createServerData();





        // Loading our whitelist.json to cache
        try {
            Whitelister.loadWhitelist();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.getLogger().info("Checking to see if there are any updates to the config...");
        //updateConfig();

        // Auto whitelist saver
        // Scheduler to run every 30 minutes and also check for IOExceptions
        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            try {
                autoSaveWhitelist();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, 1800, 1800);
        this.getLogger().info("Started autosave task for whitelist.json");


        // Admin
        this.getCommand("reloadConfig").setExecutor((new ReloadConfigs()));

        // ETC
        this.getCommand("version").setExecutor((new VersionCmd()));
        this.getCommand("motd").setExecutor((new Motd()));

        // Utils
        this.getCommand("fly").setExecutor((new Fly()));

        // Base command
        this.getCommand("pes").setExecutor((new MainCmds()));

        // Whitelist system
        this.getCommand("whitelist").setExecutor((new WhitelistCommand()));

        // Spawn
        this.getCommand("spawn").setExecutor((new Spawn()));
        this.getCommand("setspawn").setExecutor((new SetSpawn()));

        //  Homes
        this.getCommand("sethome").setExecutor((new SetHome()));
        this.getCommand("home").setExecutor((new GoHome()));
        this.getCommand("delhome").setExecutor((new DelHome()));

        // Discord
        this.getCommand("discord").setExecutor((new discord()));
        Bot bot = new Bot();
        bot.connectDiscord();

        // Events
        Bukkit.getPluginManager().registerEvents(new MemberJoin(), this);
        Bukkit.getPluginManager().registerEvents(new MemberLeave(), this);
        Bukkit.getPluginManager().registerEvents(new PreMemberJoin(), this);
        Bukkit.getPluginManager().registerEvents(new MemberChat(), this);

    }

    @Override
    public void onDisable() {
        // Saving our whitelist
        try {
            Whitelister.saveWhitelist();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
