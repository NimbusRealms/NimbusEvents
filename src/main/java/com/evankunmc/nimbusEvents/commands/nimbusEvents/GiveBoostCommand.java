package com.evankunmc.nimbusEvents.commands.nimbusEvents;

import com.evankunmc.nimbusEvents.utils.MessageManager;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class GiveBoostCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage(MessageManager.prefix + MessageManager.convertMessage("&fThis command can only be executed by players."));
            return false;
        }

        ItemStack booster = new ItemStack(Material.SUGAR, 1);
        booster.addUnsafeEnchantment(Enchantment.VANISHING_CURSE, 1);
        ItemMeta meta = booster.getItemMeta();
        meta.setCustomModelData(100);
        meta.setDisplayName(MessageManager.convertMessage("&x&5&4&D&A&F&4ɢ&x&5&4&D&7&F&2ʟ&x&5&4&D&3&F&1ᴏ&x&5&4&D&0&E&Fᴡ &x&5&4&C&9&E&Cʙ&x&5&4&C&6&E&Aᴏ&x&5&4&C&3&E&8ᴏ&x&5&4&B&F&E&7ꜱ&x&5&4&B&C&E&5ᴛ&x&5&4&B&8&E&3ᴇ&x&5&4&B&5&E&2ʀ &x&5&4&A&E&D&E(&x&5&4&A&B&D&DR&x&5&4&A&8&D&Bi&x&5&4&A&4&D&9g&x&5&4&A&1&D&8h&x&5&4&9&E&D&6t &x&5&4&9&7&D&2c&x&5&4&9&4&D&1l&x&5&4&9&0&C&Fi&x&5&4&8&D&C&Dc&x&5&4&8&A&C&Ck &x&5&4&8&3&C&8t&x&5&4&8&0&C&7o &x&5&4&7&9&C&3a&x&5&4&7&5&C&2c&x&5&4&7&2&C&0t&x&5&4&6&F&B&Ei&x&5&4&6&B&B&Dv&x&5&4&6&8&B&Ba&x&5&4&6&5&B&9t&x&5&4&6&1&B&8e&x&5&4&5&E&B&6)"));
        meta.setLore(List.of(
                MessageManager.convertMessage("&fHide & Seek booster"),
                MessageManager.convertMessage("&f - Gives glowing effect to all NPCs for 5 seconds"),
                MessageManager.convertMessage("&f - &lOne&f use only"),
                "",
                MessageManager.convertMessage("&f&oRight click to activate")
        ));
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        booster.setItemMeta(meta);

        player.getInventory().addItem(booster);
        return true;
    }
}
