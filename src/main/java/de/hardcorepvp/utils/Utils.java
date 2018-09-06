package de.hardcorepvp.utils;

import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.util.com.google.gson.JsonObject;
import net.minecraft.util.com.google.gson.JsonParser;
import net.minecraft.util.com.mojang.authlib.GameProfile;
import net.minecraft.util.com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Utils {

	public static boolean pvp = true;

	public static ConcurrentHashMap<String, Long> tpaCooldown = new ConcurrentHashMap<>();
	public static HashMap<String, String> currentTpaRequest = new HashMap<>();
	public static HashMap<String, String> currentTpahereRequest = new HashMap<>();

	public static Property getSkinData(Player player) {

		EntityPlayer playerNMS = ((CraftPlayer) player).getHandle();
		GameProfile profile = playerNMS.getProfile();
		Property property = profile.getProperties().get("textures").iterator().next();

		return new Property("textures", property.getValue(), property.getSignature());
	}

	public static String[] getSkinData(String name) {
		try {

			URL getUUID = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
			InputStreamReader reader_0 = new InputStreamReader(getUUID.openStream());
			String uuid = new JsonParser().parse(reader_0).getAsJsonObject().get("id").getAsString();

			URL getSkin = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
			InputStreamReader reader_1 = new InputStreamReader(getSkin.openStream());
			JsonObject property = new JsonParser().parse(reader_1).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
			String texture = property.get("value").getAsString();
			String signature = property.get("signature").getAsString();

			return new String[]{texture, signature};
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

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

	public static String serializeLocation(Location location) {
		return location.getWorld().getName() + ";" + location.getX() + ";" + location.getY() + ";" + location.getZ() + ";" + location.getYaw() + ";" + location.getPitch();
	}

	public static Location deserializeLocation(String location) {
		String[] deserialized = location.split(";");
		return new Location(Bukkit.getServer().getWorld(deserialized[0]), Double.valueOf(deserialized[1]), Double.valueOf(deserialized[2]), Double.valueOf(deserialized[3]), Float.valueOf(deserialized[4]), Float.valueOf(deserialized[5]));
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

	public static void killTpahereRequest(String key) {
		if (currentTpahereRequest.containsKey(key)) {
			Player player = Bukkit.getPlayer(currentTpahereRequest.get(key));
			if (player != null) {
				player.sendMessage("Deine Anfrage ist abgelaufen");
			}
			currentTpahereRequest.remove(key);

		}

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
