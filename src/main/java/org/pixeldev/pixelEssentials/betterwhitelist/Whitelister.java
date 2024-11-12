package org.pixeldev.pixelEssentials.betterwhitelist;

import com.google.gson.Gson;
import org.bukkit.configuration.file.FileConfiguration;
import org.pixeldev.pixelEssentials.PixelEssentials;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Whitelister {

    public static Boolean whitelistEnabled;
    public static HashMap<String, UUIDWhitelist> wlUUIDs = new HashMap<>();
    public static HashMap<String, wlIPs> wlIPs = new HashMap<>();

    // Loading our whitelist from the whitelist.json file in the PixelEssentials data folder
    public static void loadWhitelist() throws IOException {
        Gson gson = new Gson();
        File dataFolder = new File(PixelEssentials.getInstance().getDataFolder(), "whitelist.json");
        if (dataFolder.exists()) {
            try (InputStreamReader reader = new InputStreamReader(new FileInputStream(dataFolder), StandardCharsets.UTF_8)) {
                Map<String, Object> map = gson.fromJson(reader, Map.class);

                List<Map<String, String>> users = (List<Map<String, String>>) map.get("users");
                for (Map<String, String> user : users) {
                    String uuid = user.get("UUID");
                    String username = user.get("username");
                    wlUUIDs.put(username, new UUIDWhitelist(username, uuid));
                }

                List<String> ips = (List<String>) map.get("ips");
                for (String ip : ips) {
                    wlIPs.put(ip, new wlIPs(ip));
                }

                // Noting the enabled whitelist value
                whitelistEnabled = (Boolean) map.get("enabled");
            }
        }
    }

    // Saving our hashmap to whitelist json file.
    public static void saveWhitelist() throws IOException {
        File dataFolder = new File(PixelEssentials.getInstance().getDataFolder(), "whitelist.json");
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<>();
        List<Map<String, String>> users = new ArrayList<>();
        for (Map.Entry<String, UUIDWhitelist> entry : wlUUIDs.entrySet()) {
            Map<String, String> user = new HashMap<>();
            user.put("UUID", entry.getValue().uuid);
            user.put("username", entry.getValue().name);
            users.add(user);
        }
        map.put("users", users);

        List<String> ips = new ArrayList<>(wlIPs.keySet());
        map.put("ips", ips);

        // Saving the enabled whitelist value
        map.put("enabled", whitelistEnabled);

        String json = gson.toJson(map);
        try (FileWriter writer = new FileWriter(dataFolder)) {
            writer.write(json);
        }
    }




    public static class UUIDWhitelist {
        public final String uuid;
        public final String name;

        public UUIDWhitelist(String name, String uuid) {
            this.uuid = uuid;
            this.name = name;
        }
    }

    public static class wlIPs {
        public final String ip;

        public wlIPs(String ip) {
            this.ip = ip;
        }
    }

}
