package com.evankunmc.nimbusEvents.commands.events;

import com.evankunmc.nimbusEvents.utils.ConfigManager;
import com.evankunmc.nimbusEvents.utils.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LeaveEvent implements CommandExecutor {
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

        player.chat("/spawn");
        commandSender.sendMessage(MessageManager.convertMessage(
                MessageManager.prefix + "&lThank you for participating! You can still re-join using /events join "+strings[1]
        ));

        return false;
    }
}
