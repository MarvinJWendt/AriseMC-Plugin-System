package de.hardcorepvp.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.UUID;

public class AsyncPlayerPreLoginListener implements Listener {

	@EventHandler
	public void onAsyncLogin(AsyncPlayerPreLoginEvent event) {
		UUID uniqueId = event.getUniqueId();
		//Ban check
		long timestamp = System.currentTimeMillis();
		System.out.println("Dauer: " + (System.currentTimeMillis() - timestamp));
	}
}
