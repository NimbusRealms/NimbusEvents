package com.evankunmc.nimbusEvents.commands.events;

import com.evankunmc.nimbusEvents.NimbusEvents;
import com.evankunmc.nimbusEvents.utils.ConfigManager;
import com.evankunmc.nimbusEvents.utils.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EventsCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!commandSender.hasPermission(NimbusEvents.PERMISSION_USER)) {
            commandSender.sendMessage(MessageManager.convertMessage(
                    MessageManager.prefix + "&rSorry, but you do not have permission for that command!"
            ));
            return false;
        }

        return switch (strings[0].toLowerCase()) {
            case "join" -> new JoinEvent().onCommand(commandSender, command, s, strings);
            case "leave" -> new LeaveEvent().onCommand(commandSender, command, s, strings);
            default -> false;
        };

    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length == 1) {
            return List.of("join", "leave");
        }

        if(strings.length == 2) {
            switch(strings[0].toLowerCase()) {
                case "join":
                case "leave":
                    List<String> events = new ArrayList<>();
                    for(String event : ConfigManager.eventsConfig.getKeys(false)) {
                        Player player = Bukkit.getPlayer(commandSender.getName());
//                        MessageManager.sendPlayer(player, event);
//                        MessageManager.sendPlayer(player, ConfigManager.eventsConfig.getBoolean(event + ".enabled", false) ? "enabled" : "disabled");
                        if(ConfigManager.eventsConfig.getBoolean(event + ".enabled", false)) {
                            events.add(event);
                        }
                    }
                    return events;
            }
        }

        return List.of();
    }
}
