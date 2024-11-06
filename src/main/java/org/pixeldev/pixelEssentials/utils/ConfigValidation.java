package org.pixeldev.pixelEssentials.utils;

import org.bukkit.configuration.file.YamlConfiguration;
import org.pixeldev.pixelEssentials.PixelEssentials;

import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class ConfigValidation {
    public static void updateConfig() {
        updateDefault();
    }

    private static void updateDefault() {
        // Checking to see if the keys are there.
        PixelEssentials instance = PixelEssentials.getInstance();

        File defaultConfig = new File(instance.getDataFolder() + "/config.yml");
        YamlConfiguration defaultYmlConfig = YamlConfiguration
                .loadConfiguration(defaultConfig);

        InputStreamReader internalConfig = new InputStreamReader(Objects.requireNonNull(instance
                .getResource("config.yml")), StandardCharsets.UTF_8);
        YamlConfiguration internalCFGYaml = YamlConfiguration
                .loadConfiguration(internalConfig);

        for (String strong : internalCFGYaml.getKeys(true)) {
            if (!defaultYmlConfig.contains(strong)) {
                defaultYmlConfig.set(strong, internalCFGYaml.get(strong));
            }
        }



    }
}
