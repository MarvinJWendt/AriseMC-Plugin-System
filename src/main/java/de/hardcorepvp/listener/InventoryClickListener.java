package de.hardcorepvp.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class InventoryClickListener implements Listener {

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {

		Player player = (Player) event.getWhoClicked();
		Inventory inventory = event.getClickedInventory();

		//Fehler! Abfragen, ob das Inventar null ist
		if (inventory == null) return;
		if (inventory.getType() == InventoryType.ENDER_CHEST && inventory.getHolder() != player) {
			event.setCancelled(true);
		}
	}
}
