package com.evankunmc.nimbusEvents.commands.nimbusEvents;

import com.evankunmc.nimbusEvents.utils.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class UserIPCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length < 2) {
            commandSender.sendMessage(MessageManager.prefix + MessageManager.convertMessage("&fUser IP command requires 1 argument, but only found " + (strings.length-1)));
            return false;
        }

        Player user = Bukkit.getPlayer(strings[1]);
        if (user == null) {
            commandSender.sendMessage(MessageManager.prefix + MessageManager.convertMessage("&fUser not found: " + strings[1]));
            return false;
        }
        String ip = Objects.requireNonNull(user.getAddress()).getHostName();

        commandSender.sendMessage(MessageManager.convertMessage(
                "The IP of the user &l" + strings[1] + "&r is: &n" + ip
        ));
        return true;
    }
}
