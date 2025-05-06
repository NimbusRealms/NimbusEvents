package com.evankunmc.nimbusEvents;

import com.evankunmc.nimbusEvents.commands.events.EventsCommand;
import com.evankunmc.nimbusEvents.commands.nimbusEvents.NimbusEventsCommand;
import com.evankunmc.nimbusEvents.utils.ConfigManager;
import com.evankunmc.nimbusEvents.utils.MessageManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class NimbusEvents extends JavaPlugin {
    public static NimbusEvents plugin;
    public final static String PERMISSION_USER = "nimbusevents.event";
    public final static String PERMISSION_BYPASSALT = "nimbusevents.bypassalt";
    public final static String PERMISSION_ADMIN = "nimbusevents.admin";

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        new ConfigManager();

        try {
            Objects.requireNonNull(getCommand("events")).setExecutor(new EventsCommand());
            Objects.requireNonNull(getCommand("events")).setTabCompleter(new EventsCommand());
            Objects.requireNonNull(getCommand("nimbusevents")).setExecutor(new NimbusEventsCommand());
            Objects.requireNonNull(getCommand("nimbusevents")).setTabCompleter(new NimbusEventsCommand());
        } catch (NullPointerException e) {
            MessageManager.sendConsole("&cAn error has occurred while loading the commands");
            MessageManager.sendConsole(e.getMessage());
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
