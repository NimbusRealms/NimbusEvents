package com.evankunmc.nimbusEvents.commands.nimbusEvents;

import com.evankunmc.nimbusEvents.utils.ConfigManager;
import com.evankunmc.nimbusEvents.utils.MessageManager;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class EventListCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        commandSender.sendMessage(MessageManager.pluginHeader);
        ConfigManager.eventsConfig.getKeys(false).forEach(event -> {
            TextComponent eventList = new TextComponent(" - " + event);
            eventList.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("View Event Information")));
            eventList.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,  "/nimbusevents info " + event));

            commandSender.spigot().sendMessage(eventList);
        });
        commandSender.sendMessage(MessageManager.pluginFooter);
        return true;
    }
}
