package de.hardcorepvp.manager;

import de.hardcorepvp.Main;
import de.hardcorepvp.model.Ranking;
import net.minecraft.util.com.google.common.cache.CacheBuilder;
import net.minecraft.util.com.google.common.cache.CacheLoader;
import net.minecraft.util.com.google.common.cache.LoadingCache;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class RankingManager {

	private LoadingCache<UUID, Integer> statsSingleRankCache;
	private LoadingCache<Ranking, Map<String, Long>> rankingCache;

	public RankingManager() {
		this.statsSingleRankCache = CacheBuilder
				.newBuilder()
				.expireAfterAccess(5, TimeUnit.MINUTES)
				.build(new CacheLoader<UUID, Integer>() {
					@Override
					public Integer load(UUID uniqueId) throws Exception {
						PreparedStatement statement = Main.getDatabaseManager().getConnection().prepareStatement(
								"SELECT COUNT(*) AS `rank` FROM `user_stats`" +
										" WHERE `kills` >= (SELECT `kills` FROM `user_stats` WHERE `uniqueId`= ?)");
						statement.setString(1, uniqueId.toString());

						ResultSet resultSet = statement.executeQuery();
						int rank = -1;
						if (resultSet.next()) {
							rank = resultSet.getInt("rank");
						}
						statement.close();
						resultSet.close();
						return rank;
					}
				});
		this.rankingCache = CacheBuilder
				.newBuilder()
				.expireAfterAccess(5, TimeUnit.MINUTES)
				.build(new CacheLoader<Ranking, Map<String, Long>>() {
					@Override
					public Map<String, Long> load(Ranking ranking) throws Exception {
						if (ranking == Ranking.KILLS) {
							PreparedStatement statement = Main.getDatabaseManager().getConnection().prepareStatement("SELECT `uniqueId`, `kills` FROM `user_stats` ORDER BY `kills`");
							ResultSet resultSet = statement.executeQuery();
							Map<String, Long> rankingKills = new ConcurrentHashMap<>();
							while (resultSet.next()) {
								UUID uniqueId = UUID.fromString(resultSet.getString("uniqueId"));
								long kills = resultSet.getInt("kills");
								Player target = Bukkit.getPlayer(uniqueId);
								if (target.isOnline()) {
									rankingKills.put(target.getName(), kills);
								}
								OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uniqueId);
								if (offlinePlayer.hasPlayedBefore()) {
									rankingKills.put(offlinePlayer.getName(), kills);
								}
								rankingKills.put(uniqueId.toString(), kills);
							}
							return rankingKills;
						}
						if (ranking == Ranking.DEATHS) {
							PreparedStatement statement = Main.getDatabaseManager().getConnection().prepareStatement("SELECT `uniqueId`, `deaths` FROM `user_stats` ORDER BY `deaths`");
							ResultSet resultSet = statement.executeQuery();
							Map<String, Long> rankingDeaths = new ConcurrentHashMap<>();
							while (resultSet.next()) {
								UUID uniqueId = UUID.fromString(resultSet.getString("uniqueId"));
								long deaths = resultSet.getInt("deaths");
								Player target = Bukkit.getPlayer(uniqueId);
								if (target.isOnline()) {
									rankingDeaths.put(target.getName(), deaths);
								}
								OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uniqueId);
								if (offlinePlayer.hasPlayedBefore()) {
									rankingDeaths.put(offlinePlayer.getName(), deaths);
								}
								rankingDeaths.put(uniqueId.toString(), deaths);
							}
							return rankingDeaths;
						}
						if (ranking == Ranking.MONEY) {
							PreparedStatement statement = Main.getDatabaseManager().getConnection().prepareStatement("SELECT `uniqueId`, `money` FROM `user_money` ORDER BY `money`");
							ResultSet resultSet = statement.executeQuery();
							Map<String, Long> rankingMoney = new ConcurrentHashMap<>();
							while (resultSet.next()) {
								UUID uniqueId = UUID.fromString(resultSet.getString("uniqueId"));
								long money = resultSet.getLong("money");
								Player target = Bukkit.getPlayer(uniqueId);
								if (target.isOnline()) {
									rankingMoney.put(target.getName(), money);
								}
								OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uniqueId);
								if (offlinePlayer.hasPlayedBefore()) {
									rankingMoney.put(offlinePlayer.getName(), money);
								}
								rankingMoney.put(uniqueId.toString(), money);
							}
							return rankingMoney;
						}
						return null;
					}
				});
	}

	public void getUserStatsRank(UUID uniqueId, Consumer<Integer> consumer) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
			try {
				int rank = this.statsSingleRankCache.get(uniqueId);
				consumer.accept(rank);
			} catch (ExecutionException e) {
				e.printStackTrace();
				consumer.accept(-1);
			}
		});
	}

	public int getUserStatsRankSync(UUID uniqueId) {
		try {
			return this.statsSingleRankCache.get(uniqueId);
		} catch (ExecutionException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public void getRanking(Ranking ranking, Consumer<Map<String, Long>> consumer) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
			try {
				consumer.accept(this.rankingCache.get(ranking));
			} catch (Exception e) {
				e.printStackTrace();
				consumer.accept(null);
			}
		});
	}

	public Map<String, Long> getRankingSync(Ranking ranking) {
		try {
			return this.rankingCache.get(ranking);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}
}
