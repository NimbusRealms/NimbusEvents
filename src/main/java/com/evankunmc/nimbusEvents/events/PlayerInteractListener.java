package com.evankunmc.nimbusEvents.events;

import com.evankunmc.nimbusEvents.integration.CitizensIntegration;
import com.evankunmc.nimbusEvents.utils.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerInteractListener implements Listener {
    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        if (!event.hasItem()) {
            return;
        }

        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        if (!isBooster(event.getItem())) {
            return;
        }

        if (event.getPlayer().getWorld() != Bukkit.getWorld("events")) {
            return;
        }

        ItemStack item = event.getItem();
        CitizensIntegration.showGlowNPCs();

        // Remove the booster item
        if (item.getAmount() <= 1) {
            event.getPlayer().getInventory().removeItem(item);
        } else {
            item.setAmount(item.getAmount() - 1);
        }

        MessageManager.sendPlayer(event.getPlayer(), "&f&lGlow Booster&r activated!");
    }

    public boolean isBooster(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        return item.containsEnchantment(Enchantment.VANISHING_CURSE) && meta.hasItemFlag(ItemFlag.HIDE_ENCHANTS)
                && meta.hasLore() && meta.hasCustomModelData();
    }
}
