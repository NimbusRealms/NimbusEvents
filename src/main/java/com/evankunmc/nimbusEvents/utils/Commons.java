package com.evankunmc.nimbusEvents.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Commons {
    public static boolean inRegion(Player p, Location loc1, Location loc2) {
        double x1 = loc1.getX();
        double y1 = loc1.getY();
        double z1 = loc1.getZ();

        double x2 = loc2.getX();
        double y2 = loc2.getY();
        double z2 = loc2.getZ();

        double xP = p.getLocation().getX();
        double yP = p.getLocation().getY();
        double zP = p.getLocation().getZ();

        return ((x1 < xP && xP < x2) || (x1 > xP && xP > x2)) && ((y1 < yP && yP < y2) || (y1 > yP && yP > y2)) && ((z1 < zP && zP < z2) || (z1 > zP && zP > z2));
    }
}
