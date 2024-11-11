package org.pixeldev.pixelEssentials;

import org.bukkit.configuration.file.YamlConfiguration;
import org.pixeldev.pixelEssentials.betterwhitelist.commands.WhitelistCommand;
import org.pixeldev.pixelEssentials.commands.MainCmds;
import org.pixeldev.pixelEssentials.commands.etc.Fly;
import org.pixeldev.pixelEssentials.commands.serverAdmin.ReloadConfigs;
import org.pixeldev.pixelEssentials.commands.serverAdmin.VersionCmd;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.pixeldev.pixelEssentials.commands.Motd;
import org.pixeldev.pixelEssentials.events.MemberJoin;

import java.io.File;

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
    
    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        loadConfigs();
        FileConfiguration config = getConfig();

        this.getLogger().info("Checking to see if there are any updates to the config...");
        updateConfig();



        this.getCommand("motd").setExecutor((new Motd()));
        this.getCommand("reloadConfig").setExecutor((new ReloadConfigs()));
        this.getCommand("version").setExecutor((new VersionCmd()));
        this.getCommand("fly").setExecutor((new Fly()));

        this.getCommand("pes").setExecutor((new MainCmds()));

        this.getCommand("whitelist").setExecutor((new WhitelistCommand()));

        Bukkit.getPluginManager().registerEvents(new MemberJoin(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
