package com.evankunmc.nimbusEvents.utils;

import com.evankunmc.nimbusEvents.NimbusEvents;
import org.apache.commons.io.IOUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;

public class ConfigManager {
    public static FileConfiguration messagesConfig;
    public static FileConfiguration eventsConfig;

    public ConfigManager() {
        Bukkit.getConsoleSender().sendMessage("[NimbusEvents] Initializing Configuration");
        File dataFolder = NimbusEvents.plugin.getDataFolder();
        if (!dataFolder.exists()) dataFolder.mkdir();

        File pluginConfig = new File(dataFolder, "config.yml");
        if (!pluginConfig.exists())
            writeToFile(pluginConfig, NimbusEvents.plugin.getResource("config.yml"));

        File messagesConfig = new File(dataFolder, "messages.yml");
        if (!messagesConfig.exists())
            writeToFile(messagesConfig, NimbusEvents.plugin.getResource("messages.yml"));
        ConfigManager.messagesConfig = new YamlConfiguration();
        try {
            ConfigManager.messagesConfig.load(messagesConfig);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        File eventsConfig = new File(dataFolder, "events.yml");
        if (!eventsConfig.exists())
            writeToFile(eventsConfig, NimbusEvents.plugin.getResource("events.yml"));
        ConfigManager.eventsConfig = new YamlConfiguration();
        try {
            ConfigManager.eventsConfig.load(eventsConfig);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        File eventDataFolder = new File(dataFolder, "event-data");
        if (!eventDataFolder.exists()) {
            boolean created = eventDataFolder.mkdir();
            if (created) {
                Bukkit.getConsoleSender().sendMessage("[NimbusEvents] Created directory: " + eventDataFolder.getName());
            }
        }
    }

    public void writeToFile(File file, InputStream data) {
        try(OutputStream outputStream = new FileOutputStream(file)){
            IOUtils.copy(data, outputStream);
        } catch (FileNotFoundException e) {
            MessageManager.sendConsole("Error: Configuration files doesn't exist");
            MessageManager.sendConsole(e.getMessage());
        } catch (IOException e) {
            MessageManager.sendConsole("An error occurred while fetching file");
            MessageManager.sendConsole(e.getMessage());
        }
    }

    public static void saveEventsConfig() {
        File eventsConfig = new File(NimbusEvents.plugin.getDataFolder(), "events.yml");

        try {
            ConfigManager.eventsConfig.save(eventsConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
