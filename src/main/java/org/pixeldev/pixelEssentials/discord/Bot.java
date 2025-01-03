package org.pixeldev.pixelEssentials.discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.pixeldev.pixelEssentials.PixelEssentials;

import java.util.EnumSet;

public class Bot {
    PixelEssentials pi = PixelEssentials.getInstance();
    private static JDA jda;
    public void connectDiscord() {

        // Checking if the discord token is set

        if (pi.getConfig().getString("discord.token") == null) {
            pi.getLogger().warning("No discord token found. Please set the token in the config.yml file.");
        } else {
            // Token is set, let's connect to discord.
            pi.getLogger().info("Connecting to discord...");
            try {
                String token = pi.getConfig().getString("discord.token");
                EnumSet<GatewayIntent> intentions = EnumSet.of(
                        GatewayIntent.GUILD_MEMBERS,
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.MESSAGE_CONTENT,
                        GatewayIntent.GUILD_WEBHOOKS
                );
                jda = JDABuilder.createLight(token, intentions).build();
                pi.getLogger().info("Connected to discord.");
            } catch (Exception e) {
                pi.getLogger().warning("Failed to connect to discord.");
                // Sending error to logs
                e.printStackTrace();
            }
        }
    }

    public static JDA GetBot() {
        return jda;
    }
}
