package org.pixeldev.pixelEssentials.utils;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.ChatColor;

public class Colorize {


    public static String colorize(String content) {
        MiniMessage mm = MiniMessage.miniMessage();
        LegacyComponentSerializer cereal = LegacyComponentSerializer.legacyAmpersand();
        String result = ChatColor.translateAlternateColorCodes('&', cereal.serialize(mm.deserialize(content.replace('§', '&'))));
        return result;
    }

}
