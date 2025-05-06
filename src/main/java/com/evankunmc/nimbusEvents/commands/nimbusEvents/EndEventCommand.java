package com.evankunmc.nimbusEvents.commands.nimbusEvents;

import com.evankunmc.nimbusEvents.NimbusEvents;
import com.evankunmc.nimbusEvents.utils.Commons;
import com.evankunmc.nimbusEvents.utils.ConfigManager;
import com.evankunmc.nimbusEvents.utils.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class EndEventCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length < 2) {
            commandSender.sendMessage(MessageManager.prefix + MessageManager.convertMessage("&fEvent Start command requires 1 argument, but only found " + (strings.length-1)));
            return false;
        }

        if (!ConfigManager.eventsConfig.contains(strings[1])) {
            commandSender.sendMessage(MessageManager.prefix + MessageManager.convertMessage("&fCannot find event: &n" + strings[1]));
            return false;
        }

        ConfigManager.eventsConfig.set(strings[1] + ".enabled", false);
        ConfigManager.saveEventsConfig();

        commandSender.sendMessage(MessageManager.prefix + MessageManager.convertMessage("&fEnded event: &n" + strings[1]));

//        Announcement
        if(strings.length == 2 || !strings[2].equalsIgnoreCase("--silent")) {
            Bukkit.broadcastMessage(MessageManager.convertMessage(
                    MessageManager.prefix +
                            "The event " + ConfigManager.eventsConfig.getString(strings[1] + ".announce-title", "") +
                            " has ended! All participants within the area has been teleported to spawn"
            ));
        }

        FileConfiguration eventDataYaml = new YamlConfiguration();
        File eventDataFile = new File(NimbusEvents.plugin.getDataFolder(), "event-data/" + strings[1] + ".yml");
        try {
            if (!eventDataFile.exists()) {
                eventDataFile.createNewFile();
            }
            eventDataYaml.load(eventDataFile);

        } catch (IOException | InvalidConfigurationException e) {
            commandSender.sendMessage(MessageManager.convertMessage(MessageManager.prefix + "&cSomething went wrong. Please report to your administrator."));
            e.printStackTrace();
            return false;
        }

//        Bounds
        ConfigurationSection bounds = ConfigManager.eventsConfig.getConfigurationSection(strings[1] + ".bounds");
        Location NW = new Location(
                Bukkit.getWorld(ConfigManager.eventsConfig.getString(strings[1] + ".world", "")),
                bounds.getDouble("NW.X", 0),
                bounds.getDouble("NW.Y", 0),
                bounds.getDouble("NW.Z", 0))
                , SE = new Location(
                Bukkit.getWorld(ConfigManager.eventsConfig.getString(strings[1] + ".world", "")),
                bounds.getDouble("SE.X", 0),
                bounds.getDouble("SE.Y", 0),
                bounds.getDouble("SE.Z", 0));

        for(String entry : eventDataYaml.getKeys(false)) {
            Player player = Bukkit.getPlayer(eventDataYaml.getString(entry + ".username", ""));
            if(player != null && Commons.inRegion(player, NW, SE)) {
                player.chat("/spawn");
            }
        }

        return true;
    }
}
