package de.hardcorepvp.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Utils {

	public static ConcurrentHashMap<String, Long> tpaCooldown = new ConcurrentHashMap<>();
	public static HashMap<String, String> currentTpaRequest = new HashMap<>();
	public static HashMap<String, String> currentTpahereRequest = new HashMap<>();
	public static CMDItemEnchant uniqueEnchant = new CMDItemEnchant(1337);
	public static ItemStack itemRankup = new ItemStack(Material.BOOK);

	public static Material excavatorMaterial = Material.NOTE_BLOCK;

	public static int stackItems(Player player) {

		int stackedItems = 0;
		ItemStack[] contents = player.getInventory().getContents();

		for (int i = 0; i < contents.length; i++) {
			ItemStack item = contents[i];
			if ((item != null) && (item.getType() != Material.AIR) && (item.getAmount() > 0)) {
				if (item.getAmount() < 64) {
					int needed = 64 - item.getAmount();
					for (int i2 = i++; i2 < contents.length; i2++) {
						ItemStack nextCurrent = contents[i2];
						if ((nextCurrent != null) && (nextCurrent.getType() != Material.AIR) && (nextCurrent.getAmount() > 0)) {
							if ((item.getType() == nextCurrent.getType()) && (item.getDurability() == nextCurrent.getDurability()) && (((item.getItemMeta() == null) && (nextCurrent.getItemMeta() == null)) || ((item.getItemMeta() != null) && (item.getItemMeta().equals(nextCurrent.getItemMeta()))))) {
								if (nextCurrent.getAmount() > needed) {
									item.setAmount(64);
									nextCurrent.setAmount(nextCurrent.getAmount() - needed);
									stackedItems++;
									break;
								}
								contents[i2] = null;
								item.setAmount(item.getAmount() + nextCurrent.getAmount());
								needed = 64 - item.getAmount();

							}
						}
					}
				}
			}
		}
		return stackedItems;
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

	public static int fixItems(Player player, boolean armorfix) {

		int fixedItems = 0;
		ItemStack[] items = player.getInventory().getContents();

		for (ItemStack item : items) {
			if (item != null) {
				item.setDurability((short) 0);
				fixedItems++;

			}
		}

		if (armorfix) {
			ItemStack[] armor = player.getInventory().getArmorContents();
			for (ItemStack anArmor : armor) {
				if (anArmor != null) {
					anArmor.setDurability((short) 0);
					fixedItems++;
				}
			}
		}
		return fixedItems;
	}

	public static void registerCustomEnchantments() {
		try {
			CMDItemEnchant cmdItemEnchant = new CMDItemEnchant(1337);
			Enchantment.setAcceptingNew(true);
			Enchantment.registerEnchantment(cmdItemEnchant);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static String serializeLocation(Location location) {
		return location.getWorld().getName() + ";" + location.getX() + ";" + location.getY() + ";" + location.getZ() + ";" + location.getYaw() + ";" + location.getPitch();
	}

	public static Location deserializeLocation(String location) {
		String[] deserialized = location.split(";");
		return new Location(Bukkit.getServer().getWorld(deserialized[0]), Double.valueOf(deserialized[1]), Double.valueOf(deserialized[2]), Double.valueOf(deserialized[3]), Float.valueOf(deserialized[4]), Float.valueOf(deserialized[5]));
	}

	public static void deleteWorld(File path) {
		if (path.exists()) {
			File files[] = path.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					deleteWorld(file);
				} else {
					file.delete();
				}
			}
			path.delete();
		}
	}

	public static void killTpaRequest(String key) {
		if (currentTpaRequest.containsKey(key)) {
			Player player = Bukkit.getPlayer(currentTpaRequest.get(key));
			if (player != null) {
				player.sendMessage("Deine Anfrage ist abgelaufen");
			}
			currentTpaRequest.remove(key);
		}

	}

	//TODO MAYBE WATCH FOR PERFORMANCE AND NOT DO IT ALL AT ONCE LIKE A MADMAN
	public static void destroyCube(Location location, int radius) {
		for (int x = (radius * -1); x <= radius; x++) {
			for (int y = (radius * -1); y <= radius; y++) {
				for (int z = (radius * -1); z <= radius; z++) {
					Block block = location.getWorld().getBlockAt(location.getBlockX() + x, location.getBlockY() + y, location.getBlockZ() + z);
					if (block.getType() != Material.BEDROCK && block.getType() != Material.CHEST && block.getType() != Material.TRAPPED_CHEST && block.getType() != Material.MOB_SPAWNER) {
						block.setType(Material.AIR);
					}
				}
			}
		}
	}

	public static void killTpahereRequest(String key) {
		if (currentTpahereRequest.containsKey(key)) {
			Player player = Bukkit.getPlayer(currentTpahereRequest.get(key));
			if (player != null) {
				player.sendMessage("Deine Anfrage ist abgelaufen");
			}
			currentTpahereRequest.remove(key);
		}
	}

	public static void setCommandItem(ItemStack item, String lore, String name) {
		ItemMeta im = item.getItemMeta();
		im.setLore(Collections.singletonList(lore));
		im.setDisplayName(Messages.CMD_ITEM_PREFIX + name);
		im.addEnchant(uniqueEnchant, 1, true);
		item.setItemMeta(im);
	}

	public static ItemStack excavatorBlock(int radius) {
		ItemStack item = new ItemStack(excavatorMaterial);
		ItemMeta meta = item.getItemMeta();
		meta.setLore(Collections.singletonList(Messages.EXCAVATOR_RADIUS + radius));
		meta.setDisplayName(Messages.EXCAVATOR_BLOCK);
		meta.addEnchant(uniqueEnchant, 1, true);
		item.setItemMeta(meta);
		return item;
	}

	public static void sendTpaRequest(Player sender, Player recipient) {
		if (currentTpahereRequest.values().contains(sender.getName())) {
			Bukkit.broadcastMessage("test1");
			currentTpahereRequest.values().remove(sender.getName());
		}
		if (currentTpaRequest.values().contains(sender.getName())) {
			Bukkit.broadcastMessage("test2");
			currentTpaRequest.values().remove(sender.getName());
		}
		currentTpaRequest.put(recipient.getName(), sender.getName());
	}

	//reciever -> key sender -> value
	public static void sendTpahereRequest(Player sender, Player recipient) {

		if (currentTpahereRequest.values().contains(sender.getName())) {
			Bukkit.broadcastMessage("test1");
			currentTpahereRequest.values().remove(sender.getName());
		}
		if (currentTpaRequest.values().contains(sender.getName())) {
			Bukkit.broadcastMessage("test2");
			currentTpaRequest.values().remove(sender.getName());
		}

		currentTpahereRequest.put(recipient.getName(), sender.getName());


	}

}
