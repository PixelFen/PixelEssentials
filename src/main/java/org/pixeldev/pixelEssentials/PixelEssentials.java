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
import org.pixeldev.pixelEssentials.events.MemberJoin;
import org.pixeldev.pixelEssentials.teleportation.commands.SetSpawn;
import org.pixeldev.pixelEssentials.teleportation.commands.Spawn;

import java.io.File;
import java.io.IOException;

import static org.pixeldev.pixelEssentials.utils.ConfigValidation.updateConfig;

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
        this.saveConfig();;


    }

    // System to autosave whitelist every 30 minutes
    public void autoSaveWhitelist() throws IOException {
        Whitelister.saveWhitelist();

    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        loadConfigs();
        FileConfiguration config = getConfig();
        // Loading our whitelist.json to cache
        try {
            Whitelister.loadWhitelist();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.getLogger().info("Checking to see if there are any updates to the config...");
        updateConfig();

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



        this.getCommand("motd").setExecutor((new Motd()));
        this.getCommand("reloadConfig").setExecutor((new ReloadConfigs()));
        this.getCommand("version").setExecutor((new VersionCmd()));
        this.getCommand("fly").setExecutor((new Fly()));

        this.getCommand("pes").setExecutor((new MainCmds()));

        this.getCommand("whitelist").setExecutor((new WhitelistCommand()));

        this.getCommand("spawn").setExecutor((new Spawn()));
        this.getCommand("setspawn").setExecutor((new SetSpawn()));

        Bukkit.getPluginManager().registerEvents(new MemberJoin(), this);
        Bukkit.getPluginManager().registerEvents(new PreMemberJoin(), this);

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
