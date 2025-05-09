package com.evankunmc.nimbusEvents.commands.events;

import com.evankunmc.nimbusEvents.NimbusEvents;
import com.evankunmc.nimbusEvents.commands.HelpCommand;
import com.evankunmc.nimbusEvents.utils.ConfigManager;
import com.evankunmc.nimbusEvents.utils.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

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
            case "help" -> new HelpCommand().onCommand(commandSender, command, s, strings);
            case "join" -> new JoinEventCommand().onCommand(commandSender, command, s, strings);
            case "leave" -> new LeaveEventCommand().onCommand(commandSender, command, s, strings);
            case "glow" -> new GlowCommand().onCommand(commandSender, command, s, strings);
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
