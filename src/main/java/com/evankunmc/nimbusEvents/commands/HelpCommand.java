package com.evankunmc.nimbusEvents.commands;

import com.evankunmc.nimbusEvents.utils.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class HelpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> messages = new ArrayList<>();
        messages.add(MessageManager.pluginHeader);

        if(s.equalsIgnoreCase("events")) {
            messages.add("&l[/events command]");
            messages.add(" - &a/events join <event-name> &r- Allows players to join a running event!");
            messages.add(" - &a/events leave <event-name> &r- Allows players to leave a running event!");
        } else if(s.equalsIgnoreCase("nimbusevents")) {
            messages.add("&l[/NimbusEvents command]");
            messages.add(" - &a/nimbusevents list &r- Gets all configured events for NimbusEvents");
            messages.add(" - &a/nimbusevents info <event-name> &r- Gets information about a configured event");
            messages.add(" - &a/nimbusevents start <event-name> &r- Starts (and announces) a server event!");
            messages.add(" - &a/nimbusevents end <event-name> &r- Ends (and announces) a server event!");
            messages.add(" - &a/nimbusevents ip <player-name> &r- Get the IP of a user (for debug purpose)");
        }


        messages.add(MessageManager.pluginFooter);
        for(String msg : messages) {
            commandSender.sendMessage(MessageManager.convertMessage(msg));
        }
        return true;
    }
}
