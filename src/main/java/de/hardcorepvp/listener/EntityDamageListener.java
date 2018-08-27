package de.hardcorepvp.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageListener implements Listener {

	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent event) {

		if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {

			Player player = (Player) event.getDamager();
			Player target = (Player) event.getEntity();

		}

	}

}
