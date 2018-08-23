package de.hardcorepvp.model;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class Functions {

    public static void removeNegEffects(Player player) {
        for (PotionEffect effects : player.getActivePotionEffects()) {
            for (NegativeEffects negEffects : NegativeEffects.values()) {
                if (effects.getType().getName().equalsIgnoreCase(negEffects.name())) {
                    player.removePotionEffect(effects.getType());
                }
            }
        }
    }

    public static void fixItems(ItemStack[] items) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null) {
                items[i].setDurability((short) 0);
            }
        }
    }
}
