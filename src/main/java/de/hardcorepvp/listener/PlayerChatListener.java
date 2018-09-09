package de.hardcorepvp.listener;

import de.hardcorepvp.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {

		Player player = event.getPlayer();
		if (Utils.canBypassGlobalmute(player)) {
			String message = ChatColor.translateAlternateColorCodes('&', event.getMessage());


			//TODO Prefix, Clan, Suffix
			//%1$s -> Spieler
			//%2$s -> Nachricht
			event.setFormat("%1$s§7:§r §e%2$s");
			return;
		}
		player.sendMessage("Der Chat ist im Globalmute!");
		event.setCancelled(true);
		return;


	}
}
