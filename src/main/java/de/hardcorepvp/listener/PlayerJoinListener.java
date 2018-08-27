package de.hardcorepvp.listener;

import de.hardcorepvp.Main;
import de.hardcorepvp.data.UserHomes;
import de.hardcorepvp.data.UserMoney;
import de.hardcorepvp.data.UserStats;
import de.hardcorepvp.model.Debug;
import de.hardcorepvp.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class PlayerJoinListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		UUID uniqueId = player.getUniqueId();
		Bukkit.broadcastMessage("test");
		event.setJoinMessage(null);
		//Test
		Main.getUserManager().loadUser(uniqueId, (success) -> {
			if (!success) {
				player.kickPlayer(Messages.ERROR_OCCURRED.replace("%error%", Debug.LOAD_USER.name()));
				return;
			}
			UserStats userStats = Main.getUserManager().getUserStats(uniqueId);
			UserMoney userMoney = Main.getUserManager().getUserMoney(uniqueId);
			UserHomes userHomes = Main.getUserManager().getUserHomes(uniqueId);
			userStats.addReadyExecutor(() -> {
				player.sendMessage("Kills: " + userStats.getKills());
				player.sendMessage("Deaths: " + userStats.getDeaths());
				player.sendMessage("Rank: " + Main.getRankingManager().getUserStatsRankSync(uniqueId));
			});
			userMoney.addReadyExecutor(() -> player.sendMessage("Money: " + userMoney.getMoney()));
			userHomes.addReadyExecutor(() -> player.sendMessage("Homes: " + userHomes.getHomes().size()));
			userMoney.addReadyExecutor(() -> userMoney.setMoney(10000));
		});
	}
}
