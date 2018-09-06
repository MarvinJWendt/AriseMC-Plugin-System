package de.hardcorepvp.manager;

import de.hardcorepvp.Main;
import de.hardcorepvp.data.User;
import de.hardcorepvp.utils.Utils;
import net.minecraft.util.com.google.common.cache.CacheBuilder;
import net.minecraft.util.com.google.common.cache.CacheLoader;
import net.minecraft.util.com.google.common.cache.LoadingCache;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

public class UserManager {

	private final LoadingCache<UUID, User> userCache;
	private final ReentrantLock reentrantLock;

	public UserManager() {
		this.userCache = CacheBuilder.newBuilder()
				.build(new CacheLoader<UUID, User>() {
					@Override
					public User load(UUID uniqueId) throws Exception {
						try {
							PreparedStatement statement = Main.getDatabaseManager().getConnection().prepareStatement("SELECT a.kills, a.deaths, b.money, c.name, c.location FROM user_stats a "
									+ "INNER JOIN user_money b ON a.uniqueId = b.uniqueId "
									+ "INNER JOIN user_homes c ON a.uniqueId = c.uniqueId "
									+ "WHERE a.uniqueId = ?");
							statement.setString(1, uniqueId.toString());
							ResultSet resultSet = statement.executeQuery();

							Map<String, Location> homes = new ConcurrentHashMap<>();
							long money = 0L;
							int kills = 0;
							int deaths = 0;

							boolean statsMoney = false;
							if (!resultSet.next()) {
								return new User(uniqueId);
							}
							while (resultSet.next()) {
								if (!statsMoney) {
									money = resultSet.getLong("money");
									kills = resultSet.getInt("kills");
									deaths = resultSet.getInt("deaths");
									statsMoney = true;
								}
								if (!homes.containsKey(resultSet.getString("name"))) {
									homes.put(resultSet.getString("name"), Utils.deserializeLocation(resultSet.getString("location")));
								}
							}
							return new User(uniqueId, homes, money, kills, deaths);
						} catch (SQLException e) {
							e.printStackTrace();
							return new User(uniqueId);
						}
					}
				});
		this.reentrantLock = new ReentrantLock(true);
		this.setupTables();
	}

	public boolean isUserLoaded(UUID uniqueId) {
		return this.userCache.asMap().containsKey(uniqueId);
	}

	public void loadUser(UUID uniqueId, Consumer<Optional<User>> consumer) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
			try {
				this.reentrantLock.lock();
				if (this.isUserLoaded(uniqueId)) {
					consumer.accept(Optional.ofNullable(this.userCache.get(uniqueId)));
					return;
				}
				consumer.accept(Optional.ofNullable(this.userCache.get(uniqueId)));
			} catch (ExecutionException e) {
				e.printStackTrace();
				consumer.accept(null);
			} finally {
				this.reentrantLock.unlock();
			}
		});
	}

	public void removeUser(UUID uniqueId) {
		this.userCache.invalidate(uniqueId);
	}

	private void setupTables() {
		try {
			Main.getDatabaseManager().getConnection().setAutoCommit(false);
			PreparedStatement statement = Main.getDatabaseManager().getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS `user_money` ("
					+ " `id` INT NOT NULL AUTO_INCREMENT,"
					+ " `uniqueId` CHAR(36) NOT NULL,"
					+ " `money` BIGINT UNSIGNED NOT NULL,"
					+ " PRIMARY KEY (`id`),"
					+ " UNIQUE INDEX `uniqueId_UNIQUE` (`uniqueId` ASC))");
			statement.executeUpdate();
			statement.close();

			PreparedStatement statement1 = Main.getDatabaseManager().getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS `user_stats` ("
					+ " `id` INT NOT NULL AUTO_INCREMENT,"
					+ " `uniqueId` CHAR(36) NOT NULL,"
					+ " `kills` INT UNSIGNED NOT NULL,"
					+ " `deaths` INT UNSIGNED NOT NULL,"
					+ " PRIMARY KEY (`id`),"
					+ " UNIQUE INDEX `uniqueId_UNIQUE` (`uniqueId` ASC))");
			statement1.executeUpdate();
			statement1.close();

			PreparedStatement statement2 = Main.getDatabaseManager().getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS `user_homes` ("
					+ " `uniqueId` CHAR(36) NOT NULL,"
					+ " `name` VARCHAR(50) NOT NULL,"
					+ " `location` VARCHAR(500) NOT NULL,"
					+ " INDEX `uniqueId_UNIQUE` (`uniqueId` ASC))");
			statement2.executeUpdate();
			statement2.close();

			Main.getDatabaseManager().getConnection().commit();
			Main.getDatabaseManager().getConnection().setAutoCommit(true);
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
	}
}
