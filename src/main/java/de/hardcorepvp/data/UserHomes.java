package de.hardcorepvp.data;

import de.hardcorepvp.Main;
import de.hardcorepvp.model.DatabaseLoader;
import de.hardcorepvp.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserHomes extends DatabaseLoader {

	private UUID uniqueId;
	private Map<String, Location> homes;

	public UserHomes(UUID uniqueId) {
		this.uniqueId = uniqueId;
		this.homes = new ConcurrentHashMap<>();
		this.readFromDatabase();
	}

	public UUID getUniqueId() {
		return uniqueId;
	}

	public Map<String, Location> getHomes() {
		return homes;
	}

	public void addHome(String name, Location location) {
		this.homes.put(name, location);
		this.writeToDatabase();
	}

	public void removeHome(String name) {
		this.homes.remove(name);
		this.writeToDatabase();
	}

	public boolean existsHome(String name) {
		return this.homes.containsKey(name);
	}

	@Override
	public void writeToDatabase() {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
			try {
				Main.getDatabaseManager().getConnection().setAutoCommit(false);
				this.homes.forEach((name, location) -> {
					try {
						PreparedStatement statement = Main.getDatabaseManager().getConnection().prepareStatement(
								"INSERT INTO `user_homes` (`uniqueId`, `name`, `location`)" + "VALUES(?, ?, ?) ON " + "DUPLICATE KEY UPDATE `uniqueId` = VALUES(`uniqueId`), `name` = VALUES(`name`)" + ", `location` = VALUES(`location`)");
						statement.setString(1, this.uniqueId.toString());
						statement.setString(2, name);
						statement.setString(3, Utils.serializeLocation(location));
						statement.executeUpdate();
						statement.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				});
				Main.getDatabaseManager().getConnection().setAutoCommit(true);
				Main.getDatabaseManager().getConnection().commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public void readFromDatabase() {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
			try {
				PreparedStatement statement = Main.getDatabaseManager().getConnection().prepareStatement("SELECT " +
						"`name`, `location` FROM `user_homes` WHERE `uniqueId` = ?");
				statement.setString(1, this.uniqueId.toString());

				ResultSet resultSet = statement.executeQuery();
				while (resultSet.next()) {
					this.homes.put(resultSet.getString("name"), Utils.deserializeLocation(resultSet.getString(
							"location")));
				}
				statement.close();
				resultSet.close();
				super.setLoaded(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}
}
