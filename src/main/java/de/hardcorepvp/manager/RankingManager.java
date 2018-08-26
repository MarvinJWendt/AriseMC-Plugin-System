package de.hardcorepvp.manager;

import de.hardcorepvp.Main;
import net.minecraft.util.com.google.common.cache.CacheBuilder;
import net.minecraft.util.com.google.common.cache.CacheLoader;
import net.minecraft.util.com.google.common.cache.LoadingCache;
import org.bukkit.Bukkit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class RankingManager {

	private LoadingCache<UUID, Integer> statsSingleRankCache;

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
}
