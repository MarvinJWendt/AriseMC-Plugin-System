package de.hardcorepvp.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (player.getItemInHand() != null) {
				ItemStack item = player.getItemInHand();
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), item.getItemMeta().getLore().get(0).substring(2).replace("%p%", player.getName()));
				event.setCancelled(true);
			}

		}
	}

}


