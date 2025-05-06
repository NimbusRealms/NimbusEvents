package com.evankunmc.nimbusEvents.commands.nimbusEvents;

import com.evankunmc.nimbusEvents.NimbusEvents;
import com.evankunmc.nimbusEvents.commands.HelpCommand;
import com.evankunmc.nimbusEvents.utils.ConfigManager;
import com.evankunmc.nimbusEvents.utils.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NimbusEventsCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!commandSender.hasPermission(NimbusEvents.PERMISSION_ADMIN)) {
            commandSender.sendMessage(MessageManager.convertMessage(
                    MessageManager.prefix + "&rSorry, but you do not have permission for that command!"
            ));
            return false;
        }

        return switch (strings[0].toLowerCase()) {
            case "help" -> new HelpCommand().onCommand(commandSender, command, s, strings);
            case "start" -> new StartEventCommand().onCommand(commandSender, command, s, strings);
            case "end" -> new EndEventCommand().onCommand(commandSender, command, s, strings);
            case "info" -> new EventInfoCommand().onCommand(commandSender, command, s, strings);
            case "list" -> new EventListCommand().onCommand(commandSender, command, s, strings);
            case "ip" -> new UserIPCommand().onCommand(commandSender, command, s, strings);
            default -> false;
        };
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {

        if(strings.length == 1) {
            return List.of("start", "end", "info", "list", "ip", "help");
        }

        if(strings.length == 2) {
            switch (strings[0].toLowerCase()) {
                case "start":
                case "end":
                case "info":
                    return new ArrayList<>(ConfigManager.eventsConfig.getKeys(false));
                case "ip":
                    return Bukkit.getOnlinePlayers().stream()
                            .map(Player::getName)
                            .collect(Collectors.toList());
            }
        }

        if(strings.length == 3) {
            switch (strings[0].toLowerCase()) {
                case "start":
                case "end":
                    return List.of("--silent");
            }
        }

        return List.of();
    }
}
