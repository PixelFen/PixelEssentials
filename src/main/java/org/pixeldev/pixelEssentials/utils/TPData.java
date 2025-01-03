package org.pixeldev.pixelEssentials.utils;

import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.Nullable;
import org.pixeldev.pixelEssentials.PixelEssentials;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TPData {

    public static boolean saveSpawn(double x, double y, double z, String world, float yaw, float pitch) {
        PixelEssentials pi = PixelEssentials.getInstance();
        if (!pi.getConfig().get("storage").toString().matches("sql")) {
            pi.GetServerData().set("spawn.x", x);
            pi.GetServerData().set("spawn.y", y);
            pi.GetServerData().set("spawn.z", z);
            pi.GetServerData().set("spawn.world", world);
            pi.GetServerData().set("spawn.yaw", yaw);
            pi.GetServerData().set("spawn.pitch", pitch);
            pi.saveServerData();
            return true;
        } else {
            // TODO: Implement SQL
            return false;
        }

    }

    public static @Nullable Map<String, Object> GetSpawn() {
        PixelEssentials pi = PixelEssentials.getInstance();
        // Mini hashmap to store the home data for this uuid.
        // Will be populated depending on if its sql or yml.
        Map<String, Object> spawn = new HashMap<>(Map.of());
        if (!pi.getConfig().get("storage").toString().matches("sql")) {
            spawn.put("x", pi.GetServerData().get("spawn.x"));
            spawn.put("y", pi.GetServerData().get("spawn.y"));
            spawn.put("z", pi.GetServerData().get("spawn.z"));
            spawn.put("world", pi.GetServerData().get("spawn.world"));
            spawn.put("yaw", pi.GetServerData().get("spawn.yaw"));
            spawn.put("pitch", pi.GetServerData().get("spawn.pitch"));
        } else {
            // TODO: Implement SQL
        }
        return spawn;
    }




    public static @Nullable Map<String, Object> HomeList(UUID uuid) {
        PixelEssentials pi = PixelEssentials.getInstance();
        // Mini hashmap to store the home data for this uuid.
        // Will be populated depending on if its sql or yml.
        Map<String, Object> homes = new HashMap<>(Map.of());
        if (!pi.getConfig().get("storage").toString().matches("sql")) {
            // Checking if the home exists
            if (pi.PlayerData().contains(uuid.toString() + ".homes")) {
                ConfigurationSection homeData = pi.PlayerData().getConfigurationSection(uuid.toString() + ".homes");
                for (String home : homeData.getKeys(false)) {
                    homes.put(home, homeData.get(home));
                }
            }
        } else {
            // TODO: Implement SQL
        }
        return homes;
    }

    public static boolean saveHome(UUID uuid, String home, double x, double y, double z, String world, float yaw, float pitch) {
        PixelEssentials pi = PixelEssentials.getInstance();

        if (!pi.getConfig().get("storage").toString().matches("sql")) {
            pi.PlayerData().set(uuid.toString() + ".homes." + home + ".x", x);
            pi.PlayerData().set(uuid.toString() + ".homes." + home + ".y", y);
            pi.PlayerData().set(uuid.toString() + ".homes." + home + ".z", z);
            pi.PlayerData().set(uuid.toString() + ".homes." + home + ".world", world);
            pi.PlayerData().set(uuid.toString() + ".homes." + home + ".yaw", yaw);
            pi.PlayerData().set(uuid.toString() + ".homes." + home + ".pitch", pitch);
            pi.savePlayerData();
            return true;
        } else {
            // TODO: Implement SQL
            return false;
        }
    }


    public static @Nullable Map<String, Object> GetHome(UUID uuid, String home) {
        PixelEssentials pi = PixelEssentials.getInstance();
        // Mini hashmap to store the home data for this uuid.
        // Will be populated depending on if its sql or yml.
        Map<String, Object> homes = new HashMap<>(Map.of());


        // Defaulting to yml storage if the storage option is not "sql".
        if (!pi.getConfig().get("storage").toString().matches("sql")) {
            // Checking if the home exists
            if (pi.PlayerData().contains(uuid.toString() + ".homes." + home)) {
                ConfigurationSection homeData = pi.PlayerData().getConfigurationSection(uuid.toString() + ".homes." + home);

                assert homeData != null;
                homes.put("x", homeData.getDouble("x"));
                homes.put("y", homeData.getDouble("y"));
                homes.put("z", homeData.getDouble("z"));
                homes.put("world", homeData.getString("world"));

                // Having these as flaots
                homes.put("yaw", (float) homeData.getDouble("yaw"));
                homes.put("pitch", (float) homeData.getDouble("pitch"));

            }

        } else {
            // TODO: Implement SQL
            return null;
        }

        return homes;

        
    }
}
