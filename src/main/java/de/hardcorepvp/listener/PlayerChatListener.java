package de.hardcorepvp.listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {

		Player player = event.getPlayer();
		String message = ChatColor.translateAlternateColorCodes('&', event.getMessage());

		//TODO Prefix, Clan, Suffix
		event.setFormat(player.getName() + "§7:§r " + message);

	}

}
