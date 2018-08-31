package de.hardcorepvp.data;

import de.hardcorepvp.Main;
import de.hardcorepvp.model.DatabaseLoader;
import org.bukkit.Bukkit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserStats extends DatabaseLoader {

	private UUID uniqueId;
	private int kills;
	private int deaths;

	public UserStats(UUID uniqueId) {
		this.uniqueId = uniqueId;
		this.kills = -1;
		this.deaths = -1;
		this.readFromDatabase();
	}

	public UUID getUniqueId() {
		return uniqueId;
	}

	public int getKills() {
		return kills;
	}

	public int getDeaths() {
		return deaths;
	}

	@Override
	public void writeToDatabase() {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
			try {
				PreparedStatement statement = Main.getDatabaseManager().getConnection().prepareStatement("INSERT INTO " + "`user_stats` (`uniqueId`, `kills`, "
						+ "`deaths`)" + "VALUES(?, ?, ?) ON DUPLICATE KEY UPDATE " + "`uniqueId` = VALUES(`uniqueId`), `kills` = VALUES(`kills`), `deaths` = " + "VALUES(`deaths`)");
				statement.setString(1, this.uniqueId.toString());
				statement.setInt(2, this.kills);
				statement.setInt(3, this.deaths);
				statement.executeUpdate();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public void readFromDatabase() {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
			try {
				PreparedStatement statement = Main.getDatabaseManager().getConnection().prepareStatement("SELECT " + "`kills`, `deaths` FROM `user_stats` " +
						"WHERE `uniqueId` = ?");
				statement.setString(1, this.uniqueId.toString());

				ResultSet resultSet = statement.executeQuery();
				if (resultSet.next()) {
					this.kills = resultSet.getInt("kills");
					this.deaths = resultSet.getInt("deaths");
				} else {
					this.kills = 0;
					this.deaths = 0;
					this.writeToDatabase();
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
