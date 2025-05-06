package com.evankunmc.nimbusEvents.commands.nimbusEvents;

import com.evankunmc.nimbusEvents.utils.ConfigManager;
import com.evankunmc.nimbusEvents.utils.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;

public class EventInfoCommand implements CommandExecutor {
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

        ConfigurationSection eventSection = ConfigManager.eventsConfig.getConfigurationSection(strings[1]);

        commandSender.sendMessage(MessageManager.pluginHeader);
        commandSender.sendMessage(MessageManager.convertMessage("&lEvent Informtion &r- " + strings[1]));
        commandSender.sendMessage(MessageManager.convertMessage(" | Event Status: " + (eventSection.getBoolean("enabled", false) ? "On-going" : "Stopped")));
        commandSender.sendMessage(MessageManager.convertMessage(" | Allowed Alts: " + (eventSection.getBoolean("no-alts", false) ? "No Alts" : "Alts allowed")));
        commandSender.sendMessage(MessageManager.convertMessage(
                " | Event Location: (" +
                eventSection.getString("world", "") + ") " +
                eventSection.getDouble("X", 0) + ", " +
                eventSection.getDouble("Y", 0) + ", " +
                eventSection.getDouble("Z", 0)
        ));
        commandSender.sendMessage(MessageManager.pluginFooter);

        return true;
    }
}
