package com.evankunmc.nimbusEvents.integration;

import com.evankunmc.nimbusEvents.utils.MessageManager;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public class CitizensIntegration {
    public static void showGlowNPCs() {
        for (int id = 33; id < 52; id++) {
            NPC npc = CitizensAPI.getNPCRegistry().getById(id);
            if (npc == null || npc.getEntity() == null) continue;

            World world = Bukkit.getWorld("events");
            for (LivingEntity entity : Objects.requireNonNull(world).getLivingEntities()) {
                if(entity.getLocation().getChunk().isEntitiesLoaded() && entity.getLocation().getChunk().isEntitiesLoaded()) {
                    if (npc.getEntity() != null && entity.getUniqueId().equals(npc.getEntity().getUniqueId())) {
                        PotionEffect effect = new PotionEffect(PotionEffectType.GLOWING, 5*20, 0, false, false);
                        entity.addPotionEffect(effect);
                    }
                }
            }
        }
    }
}
