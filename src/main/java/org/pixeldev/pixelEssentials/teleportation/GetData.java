package org.pixeldev.pixelEssentials.teleportation;

import com.google.gson.Gson;
import it.unimi.dsi.fastutil.Hash;
import org.bukkit.configuration.file.FileConfiguration;
import org.pixeldev.pixelEssentials.PixelEssentials;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class GetData {
    // Loading simple things into a cache

    // Spawn location
    public static double x = 0;
    public static double y = 0;
    public static double z = 0;
    // world
    public static String world = "";



    public static void loadSpawn() throws FileNotFoundException {
        // Loading our tpdata.json file with GSon
        Gson gson = new Gson();
        PixelEssentials instance = PixelEssentials.getInstance();
        File dataFolder = new File(instance.getDataFolder(), "tpdata.json");
        if (dataFolder.exists()) {
            try (Reader reader = new InputStreamReader(new FileInputStream(dataFolder), StandardCharsets.UTF_8)) {
                Map<String, Object> data = gson.fromJson(reader, Map.class);
                Map<String, Object> spawnData = (Map<String, Object>) data.get("spawn");
                GetData.x = (double) spawnData.get("x");
                GetData.y = (double) spawnData.get("y");
                GetData.z = (double) spawnData.get("z");
                GetData.world = (String) spawnData.get("world");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void loadWarps() {
        // Loading our tpdata.json file with GSon
        Gson gson = new Gson();
        PixelEssentials instance = PixelEssentials.getInstance();
        File dataFolder = new File(instance.getDataFolder(), "tpdata.json");
        if (dataFolder.exists()) {
            try (Reader reader = new InputStreamReader(new FileInputStream(dataFolder), StandardCharsets.UTF_8)) {
                Map data = gson.fromJson(reader, Map.class);
                Map<String, Object> warpData = (Map<String, Object>) data.get("warps");
                for (Map.Entry<String, Object> entry : warpData.entrySet()) {
                    String name = entry.getKey();
                    Map<String, Object> warp = (Map<String, Object>) entry.getValue();
                    double x = (double) warp.get("x");
                    double y = (double) warp.get("y");
                    double z = (double) warp.get("z");
                    String world = (String) warp.get("world");
                    serverWarps.put(name, new WarpData(name, world, x, y, z));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    // server warp hashmap
    public static HashMap<String, WarpData> serverWarps = new HashMap<>();
    public static class WarpData {
        public String name;
        public String world;
        public double x;
        public double y;
        public double z;

        public WarpData(String name, String world, double x, double y, double z) {
            this.name = name;
            this.world = world;
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }


    public static void saveTPData() {
        // Saving our tpdata.json file with GSon
        Gson gson = new Gson();
        PixelEssentials instance = PixelEssentials.getInstance();

        Map<String, Object> spawnData = new HashMap<>();
        spawnData.put("x", x);
        spawnData.put("y", y);
        spawnData.put("z", z);
        spawnData.put("world", world);

        Map<String, Object> data = new HashMap<>();
        data.put("spawn", spawnData);

        // Saving our server warps
        Map<String, Object> warpData = new HashMap<>();
        for (Map.Entry<String, WarpData> entry : serverWarps.entrySet()) {
            String name = entry.getKey();
            WarpData warp = entry.getValue();
            warpData.put(name, warp);
        }
        data.put("warps", warpData);


        File dataFolder = new File(instance.getDataFolder(), "tpdata.json");
        try (FileWriter writer = new FileWriter(dataFolder)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
