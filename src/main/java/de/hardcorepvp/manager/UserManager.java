package de.hardcorepvp.manager;

import de.hardcorepvp.Main;
import de.hardcorepvp.data.UserHomes;
import de.hardcorepvp.data.UserMoney;
import de.hardcorepvp.data.UserStats;
import org.bukkit.Bukkit;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class UserManager {

	private Map<UUID, UserStats> userStatsMap;
	private Map<UUID, UserMoney> userMoneyMap;
	private Map<UUID, UserHomes> userHomesMap;

	public UserManager() {
		this.userStatsMap = new ConcurrentHashMap<>();
		this.userMoneyMap = new ConcurrentHashMap<>();
		this.userHomesMap = new ConcurrentHashMap<>();
		this.setupTables();
	}

	public Map<UUID, UserStats> getUserStatsMap() {
		return userStatsMap;
	}

	public Map<UUID, UserMoney> getUserMoneyMap() {
		return userMoneyMap;
	}

	public Map<UUID, UserHomes> getUserHomesMap() {
		return userHomesMap;
	}

	public UserStats getUserStats(UUID uniqueId) {
		if (!this.userStatsMap.containsKey(uniqueId)) {
			UserStats userStats = new UserStats(uniqueId);
			this.userStatsMap.put(uniqueId, userStats);
			return userStats;
		}
		return this.userStatsMap.get(uniqueId);
	}

	public UserMoney getUserMoney(UUID uniqueId) {
		if (!this.userMoneyMap.containsKey(uniqueId)) {
			UserMoney userMoney = new UserMoney(uniqueId);
			this.userMoneyMap.put(uniqueId, userMoney);
			return userMoney;
		}
		return this.userMoneyMap.get(uniqueId);
	}

	public UserHomes getUserHomes(UUID uniqueId) {
		if (!this.userHomesMap.containsKey(uniqueId)) {
			UserHomes userHomes = new UserHomes(uniqueId);
			this.userHomesMap.put(uniqueId, userHomes);
			return userHomes;
		}
		return this.userHomesMap.get(uniqueId);
	}

	public void loadUser(UUID uniqueId, Consumer<Boolean> consumer) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
			if (!this.userStatsMap.containsKey(uniqueId)) {
				UserStats userStats = new UserStats(uniqueId);
				this.userStatsMap.put(uniqueId, userStats);
			}
			if (!this.userMoneyMap.containsKey(uniqueId)) {
				UserMoney userMoney = new UserMoney(uniqueId);
				this.userMoneyMap.put(uniqueId, userMoney);
			}
			if (!this.userHomesMap.containsKey(uniqueId)) {
				UserHomes userHomes = new UserHomes(uniqueId);
				this.userHomesMap.put(uniqueId, userHomes);
			}
			consumer.accept(true);
		});
	}

	public void removeUser(UUID uniqueId) {
		this.userStatsMap.remove(uniqueId);
		this.userMoneyMap.remove(uniqueId);
		this.userHomesMap.remove(uniqueId);
	}

	private void setupTables() {
		try {
			Main.getDatabaseManager().getConnection().setAutoCommit(false);
			PreparedStatement statement = Main.getDatabaseManager().getConnection().prepareStatement("CREATE TABLE IF "
					+ "NOT EXISTS `user_money` (" + " `id` INT NOT NULL AUTO_INCREMENT," + " `uniqueId` CHAR(36) NOT " + "NULL," + " `money` BIGINT UNSIGNED NOT NULL," + " PRIMARY KEY (`id`)," + " UNIQUE INDEX " + "`uniqueId_UNIQUE` (`uniqueId` ASC))");
			statement.executeUpdate();
			statement.close();

			PreparedStatement statement1 =
					Main.getDatabaseManager().getConnection().prepareStatement("CREATE TABLE " + "IF" + " NOT EXISTS " +
							"`user_stats` (" + " `id` INT NOT NULL AUTO_INCREMENT," + " `uniqueId` CHAR(36)" + " NOT " + "NULL," + " `kills` INT UNSIGNED NOT NULL," + " `deaths` INT UNSIGNED NOT NULL," + " " + "PRIMARY KEY " + "(`id`)," + " UNIQUE INDEX `uniqueId_UNIQUE` (`uniqueId` ASC))");
			statement1.executeUpdate();
			statement1.close();

			PreparedStatement statement2 =
					Main.getDatabaseManager().getConnection().prepareStatement("CREATE TABLE " + "IF" + " NOT EXISTS" +
							" " +
							"`user_homes` (" + " `uniqueId` CHAR(36) NOT NULL," + " `name` VARCHAR(50) NOT" + " " +
							"NULL," + " `location` VARCHAR(50) NOT NULL," + " INDEX `uniqueId_UNIQUE` (`uniqueId` ASC)" +
							")");
			statement2.executeUpdate();
			statement2.close();

			Main.getDatabaseManager().getConnection().commit();
			Main.getDatabaseManager().getConnection().setAutoCommit(true);
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
	}
}
