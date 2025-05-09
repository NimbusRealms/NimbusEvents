package com.evankunmc.nimbusEvents.commands.events;

import com.evankunmc.nimbusEvents.integration.CitizensIntegration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GlowCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        CitizensIntegration.showGlowNPCs();
        return true;
    }
}
