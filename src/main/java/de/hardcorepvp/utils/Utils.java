package de.hardcorepvp.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import java.util.HashMap;

public class Utils {

	public static boolean pvp = true;

	public static void stackItems(Player player) {

		ItemStack[] contents = player.getInventory().getContents();

		for (int i = 0; i < contents.length; i++) {
			ItemStack current = contents[i];
			if ((current != null) && (current.getType() != Material.AIR) && (current.getAmount() > 0)) {
				if (current.getAmount() < 64) {
					int needed = 64 - current.getAmount();
					for (int i2 = i + 1; i2 < contents.length; i2++) {
						ItemStack nextCurrent = contents[i2];
						if ((nextCurrent != null) && (nextCurrent.getType() != Material.AIR) && (nextCurrent.getAmount() > 0)) {
							if ((current.getType() == nextCurrent.getType()) && (current.getDurability() == nextCurrent.getDurability()) && (((current.getItemMeta() == null) && (nextCurrent.getItemMeta() == null)) || ((current.getItemMeta() != null) && (current.getItemMeta().equals(nextCurrent.getItemMeta()))))) {
								if (nextCurrent.getAmount() > needed) {
									current.setAmount(64);
									nextCurrent.setAmount(nextCurrent.getAmount() - needed);
									break;
								}
								contents[i2] = null;
								current.setAmount(current.getAmount() + nextCurrent.getAmount());
								needed = 64 - current.getAmount();

							}
						}
					}
				}
			}
		}
	}

	public static void renameItemInHand(Player player, String name) {

		String itemname = ChatColor.translateAlternateColorCodes('&', name);

		ItemStack item = player.getItemInHand();
		ItemMeta itemmeta = item.getItemMeta();

		itemmeta.setDisplayName(itemname);
		item.setItemMeta(itemmeta);

	}

	public static void removeNegativePotions(Player player) {

		for (PotionEffect effects : player.getActivePotionEffects()) {
			for (NegativeEffects negEffects : NegativeEffects.values()) {
				if (effects.getType().getName().equalsIgnoreCase(negEffects.name())) {
					player.removePotionEffect(effects.getType());
				}
			}
		}

	}

	public static void fixItems(Player player, boolean armorfix) {

		ItemStack[] items = player.getInventory().getContents();

		for (ItemStack item : items) {
			if (item != null) {
				item.setDurability((short) 0);
			}
		}

		if (armorfix) {
			ItemStack[] armor = player.getInventory().getArmorContents();
			for (ItemStack anArmor : armor) {
				if (anArmor != null) {
					anArmor.setDurability((short) 0);
				}
			}
		}
	}

	public static String serializeLocation(Location location) {
		return location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + "," + location.getYaw() + "," + location.getPitch();
	}

	public static Location deserializeLocation(String location) {
		String[] deserialized = location.split(",");
		return new Location(Bukkit.getServer().getWorld(deserialized[0]), Double.valueOf(deserialized[1]), Double.valueOf(deserialized[2]), Double.valueOf(deserialized[3]), Float.valueOf(deserialized[4]), Float.valueOf(deserialized[5]));
	}

}
