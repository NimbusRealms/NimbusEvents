package com.evankunmc.nimbusEvents.commands.nimbusEvents;

import com.evankunmc.nimbusEvents.utils.ConfigManager;
import com.evankunmc.nimbusEvents.utils.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class StartEventCommand implements CommandExecutor {
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

        ConfigManager.eventsConfig.set(strings[1] + ".enabled", true);
        ConfigManager.saveEventsConfig();

        commandSender.sendMessage(MessageManager.prefix + MessageManager.convertMessage("&fStarted event: &n" + strings[1]));

//        Announcement
        if(strings.length == 2 || !strings[2].equalsIgnoreCase("--silent")) {
            Bukkit.broadcastMessage(MessageManager.convertMessage(
                    MessageManager.prefix +
                    "The event " + ConfigManager.eventsConfig.getString(strings[1] + ".announce-title", "") +
                    " has started! Do [/events join " + strings[1] + "] to participate!"
                    ));
        }

        return true;
    }
}
