package com.evankunmc.nimbusEvents.commands.events;

import com.evankunmc.nimbusEvents.NimbusEvents;
import com.evankunmc.nimbusEvents.utils.ConfigManager;
import com.evankunmc.nimbusEvents.utils.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.Objects;

public class JoinEventCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage(MessageManager.prefix + MessageManager.convertMessage("&f/events can only be executed by players."));
            return false;
        }

        if (strings.length < 2) {
            commandSender.sendMessage(MessageManager.prefix + MessageManager.convertMessage("&fEvent Start command requires 1 argument, but only found " + (strings.length-1)));
            return false;
        }

        if (!ConfigManager.eventsConfig.contains(strings[1])) {
            commandSender.sendMessage(MessageManager.prefix + MessageManager.convertMessage("&fCannot find event: &n" + strings[1]));
            return false;
        }

        if (!ConfigManager.eventsConfig.getBoolean(strings[1] + ".enabled")) {
            commandSender.sendMessage(MessageManager.prefix + MessageManager.convertMessage("&fCannot join event: &n" + strings[1]));
            return false;
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

        String ipAddress = Objects.requireNonNull(player.getAddress()).getHostName().replaceAll("\\.", "-");

        if (!eventDataYaml.contains(ipAddress)) {
            eventDataYaml.set(ipAddress + ".username", commandSender.getName());
            eventDataYaml.set(ipAddress + ".createdAt", Instant.now().toString());

            try {
                eventDataYaml.save(eventDataFile);
            } catch (IOException e) {
                commandSender.sendMessage(MessageManager.convertMessage(MessageManager.prefix + "&cSomething went wrong. Please report to your administrator."));
                e.printStackTrace();
                return false;
            }
        } else {
            if(!Objects.equals(eventDataYaml.getString(ipAddress + ".username"), commandSender.getName())) {
                if(ConfigManager.eventsConfig.getBoolean(strings[1] + ".no-alts", false)) {
                    if(!commandSender.hasPermission(NimbusEvents.PERMISSION_BYPASSALT)) {
                        commandSender.sendMessage(MessageManager.convertMessage(
                                MessageManager.prefix +
                                        "&cYou may not join the event&r. Another user within your network (IP Address) has joined. " +
                                        "Please contact the staff if you think this is an error or you are not an ALT!"
                        ));

                        return false;
                    }
                }
            }
        }

        Location eventSpawn = new Location(
                Bukkit.getWorld(ConfigManager.eventsConfig.getString(strings[1] + ".world", "")),
                ConfigManager.eventsConfig.getDouble(strings[1] + ".X", 0),
                ConfigManager.eventsConfig.getDouble(strings[1] + ".Y", 0),
                ConfigManager.eventsConfig.getDouble(strings[1] + ".Z", 0)
        );

        commandSender.sendMessage(MessageManager.convertMessage(
                MessageManager.prefix + "&lTeleporting you to the event spawn point."
        ));
        player.teleport(eventSpawn);
        player.setRotation(-90, 0);
        commandSender.sendMessage(MessageManager.convertMessage(
                MessageManager.prefix + "&lGood luck and Enjoy!"
        ));
        return true;
    }
}
