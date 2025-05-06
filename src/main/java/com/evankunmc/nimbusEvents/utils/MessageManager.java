package com.evankunmc.nimbusEvents.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Map;

public class MessageManager {
    public static String prefix = convertMessage(ConfigManager.messagesConfig.getString("messages.prefix", "&5&lNimbus&6&lEvents&7 »") + "&f ");
    public static String pluginHeader = convertMessage("&7-------------------&8[ &5&lNimbus&6&lEvents&8 ]&7------------------");
    public static String pluginFooter = convertMessage("&7=====================================================");

    public static void sendConsole(String message) {
        Bukkit.getConsoleSender().sendMessage(prefix + message);
    }

    public static void sendPlayer(Player player, String message) {
        player.sendMessage(prefix + convertMessage(message));
    }

    public static String convertMessage(String message) {
        return convertMessage(message, null, false);
    }

    public static String convertMessage(String message, Map<String, String> findReplace, boolean replaceAll) {
        String msg = message;

        if (findReplace != null) {
            for(String find : findReplace.keySet()) {
                if(replaceAll) {
                    msg = msg.replaceAll(find, findReplace.get(find));
                } else {
                    msg = msg.replace(find, findReplace.get(find));
                }
            }
        }

        msg = ChatColor.translateAlternateColorCodes('&', msg);

        return msg;
    }
}
