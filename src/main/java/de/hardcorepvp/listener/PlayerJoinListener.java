package de.hardcorepvp.listener;

import de.hardcorepvp.Main;
import de.hardcorepvp.data.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

public class PlayerJoinListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		UUID uniqueId = player.getUniqueId();
		Bukkit.broadcastMessage("test");
		event.setJoinMessage(null);
		//Test
		Main.getUserManager().loadUser(uniqueId, new Consumer<Optional<User>>() {
			@Override
			public void accept(Optional<User> optionalUser) {
				if (!optionalUser.isPresent()) {
					Bukkit.getScheduler().runTask(Main.getInstance(), () -> player.kickPlayer("Es ist ein Fehler aufgetreten!"));
					return;
				}
				User user = optionalUser.get();
				if (!player.hasPlayedBefore()) {
					user.setMoney(5231123);
				}
				user.setMoney(21231);
				user.setDeaths(Integer.MAX_VALUE);
				user.setKills(Integer.MAX_VALUE);
				user.addHome("test", player.getLocation());

				player.sendMessage("Money: " + user.getMoney());
				player.sendMessage("Kills: " + user.getKills());
				player.sendMessage("Deaths: " + user.getDeaths());
				player.sendMessage("K/D: " + user.getKD());
				player.sendMessage("Rank: " + Main.getRankingManager().getUserStatsRankSync(uniqueId));
			}
		});
	}
}
