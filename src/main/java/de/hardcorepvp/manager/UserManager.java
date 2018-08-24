package de.hardcorepvp.manager;

import de.hardcorepvp.Main;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserManager {

	private Map<UUID, User> users;

	public UserManager() {
		this.users = new ConcurrentHashMap<>();
		this.setupTables();
	}

	public Map<UUID, User> getUsers() {
		return users;
	}

	public User getUser(UUID uniqueId) {
		if (uniqueId == null || !this.users.containsKey(uniqueId)) {
			return null;
		}
		return this.users.get(uniqueId);
	}

	public void addUser(UUID uniqueId) {
		if (uniqueId == null || this.users.containsKey(uniqueId)) {
			return;
		}
		User user = new User(uniqueId);
		this.users.put(uniqueId, user);
	}

	public void removeUser(UUID uniqueId) {
		if (uniqueId == null) {
			return;
		}
		this.users.remove(uniqueId);
	}

	private void setupTables() {
		try {
			Main.getDatabaseManager().getConnection().setAutoCommit(false);
			PreparedStatement statement = Main.getDatabaseManager().getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS `user_money` (" + "  `id` INT NOT NULL AUTO_INCREMENT," + "  `uniqueId` CHAR(36) NOT NULL," + "  `money` BIGINT UNSIGNED NOT NULL," + "  PRIMARY KEY (`id`)," + "  UNIQUE INDEX `uniqueId_UNIQUE` (`uniqueId` ASC))");
			statement.executeUpdate();
			statement.close();

			PreparedStatement statement1 = Main.getDatabaseManager().getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS `user_stats` (" + "  `id` INT NOT NULL AUTO_INCREMENT," + "  `uniqueId` CHAR(36) NOT NULL," + "  `kills` INT UNSIGNED NOT NULL," + "  `deaths` INT UNSIGNED NOT NULL," + "  PRIMARY KEY (`id`)," + "  UNIQUE INDEX `uniqueId_UNIQUE` (`uniqueId` ASC))");
			statement1.executeUpdate();
			statement1.close();

			PreparedStatement statement2 = Main.getDatabaseManager().getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS `user_homes` (" + "  `id` INT NOT NULL AUTO_INCREMENT," + "  `uniqueId` CHAR(36) NOT NULL," + "  `name` VARCHAR(50) NOT NULL," + "  `location` VARCHAR(50) NOT NULL," + "  PRIMARY KEY (`id`)," + "  INDEX `uniqueId_UNIQUE` (`uniqueId` ASC))");
			statement2.executeUpdate();
			statement2.close();

			Main.getDatabaseManager().getConnection().commit();
			Main.getDatabaseManager().getConnection().setAutoCommit(true);
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
	}
}
