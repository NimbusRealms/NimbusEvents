package com.evankunmc.nimbusEvents;

import com.evankunmc.nimbusEvents.commands.events.EventsCommand;
import com.evankunmc.nimbusEvents.commands.nimbusEvents.NimbusEventsCommand;
import com.evankunmc.nimbusEvents.utils.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class NimbusEvents extends JavaPlugin {
    public static NimbusEvents plugin;
    public final static String PERMISSION_USER = "nimbusevents.event";
    public final static String PERMISSION_BYPASSALT = "nimbusevents.bypassalt";
    public final static String PERMISSION_ADMIN = "nimbusevents.admin";

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        getCommand("events").setExecutor(new EventsCommand());
        getCommand("events").setTabCompleter(new EventsCommand());
        getCommand("nimbusevents").setExecutor(new NimbusEventsCommand());
        getCommand("nimbusevents").setTabCompleter(new NimbusEventsCommand());

        new ConfigManager();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
