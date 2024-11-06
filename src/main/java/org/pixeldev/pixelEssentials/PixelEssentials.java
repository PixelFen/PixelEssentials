package org.pixeldev.pixelEssentials;

import org.bukkit.configuration.file.YamlConfiguration;
import org.pixeldev.pixelEssentials.betterwhitelist.commands.WhitelistCommand;
import org.pixeldev.pixelEssentials.commands.MainCmds;
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

    public static File whitelistCFile;
    private FileConfiguration whitelistConfig;

    private void createWhitelistConfig() {
        whitelistCFile = new File(getDataFolder(), "whitelist.yml");

        if (!whitelistCFile.exists()) {
            whitelistCFile.getParentFile().mkdirs();
            saveResource("whitelist.yml", false);
        }
    }

    public FileConfiguration getWhitelistConfig() {
        return this.whitelistConfig;
    }

    // This method saves the whitelist config
    public void saveWhitelistConfig() {
        try {
            getWhitelistConfig().save(whitelistCFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadConfigs(){
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();;

        createWhitelistConfig();
        this.whitelistConfig = YamlConfiguration.loadConfiguration(whitelistCFile);



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

        this.getCommand("pes").setExecutor((new MainCmds()));

        this.getCommand("whitelist").setExecutor((new WhitelistCommand()));

        Bukkit.getPluginManager().registerEvents(new MemberJoin(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
