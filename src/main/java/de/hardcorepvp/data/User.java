package de.hardcorepvp.data;

import de.hardcorepvp.Main;
import de.hardcorepvp.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class User {

	private UUID uniqueId;
	private Map<String, Location> homeMap;
	private long money;
	private int kills;
	private int deaths;

	public User(UUID uniqueId, Map<String, Location> homes, long money, int kills, int deaths) {
		this.uniqueId = uniqueId;
		this.homeMap = homes;
		this.money = money;
		this.kills = kills;
		this.deaths = deaths;
	}

	public User(UUID uniqueId) {
		this.uniqueId = uniqueId;
		this.homeMap = new ConcurrentHashMap<>();
		this.money = 0L;
		this.kills = 0;
		this.deaths = 0;
	}

	public UUID getUniqueId() {
		return uniqueId;
	}

	public Map<String, Location> getHomes() {
		return homeMap;
	}

	public long getMoney() {
		return money;
	}

	public int getKills() {
		return kills;
	}

	public int getDeaths() {
		return deaths;
	}

	public void addHome(String name, Location location) {
		this.homeMap.put(name, location);
		this.writeToDatabase(true, false, false);
	}

	public void removeHome(String name) {
		this.homeMap.remove(name);
		this.writeToDatabase(true, false, false);
	}

	public void addMoney(long money) {
		this.money += money;
		this.writeToDatabase(false, true, false);
	}

	public void setMoney(long money) {
		this.money = money;
		this.writeToDatabase(false, true, false);
	}

	public void removeMoney(long money) {
		this.money -= money;
		this.writeToDatabase(false, true, false);
	}

	public void addKill() {
		this.kills++;
		this.writeToDatabase(false, false, true);
	}

	public void setKills(int kills) {
		this.kills = kills;
		this.writeToDatabase(false, false, true);
	}

	public void addDeath() {
		this.deaths++;
		this.writeToDatabase(false, false, true);
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
		this.writeToDatabase(false, false, true);
	}

	public double getKD() {
		if (getKills() <= 0) {
			return 0.0D;
		}
		if (getDeaths() <= 0) {
			return getKills();
		}
		BigDecimal bigDecimal = new BigDecimal(getKills() / getDeaths());
		bigDecimal.setScale(2, 4);
		return bigDecimal.doubleValue();
	}

	private void writeToDatabase(boolean homes, boolean money, boolean stats) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
			try {
				Main.getDatabaseManager().getConnection().setAutoCommit(false);
				if (homes) {
					PreparedStatement statement = Main.getDatabaseManager().getConnection().prepareStatement("INSERT INTO `user_homes` (`uniqueId`, `name`, `location`)" +
							"VALUES(?, ?, ?) ON DUPLICATE KEY UPDATE `uniqueId` = VALUES(`uniqueId`), `name` = VALUES(`name`), `location` = VALUES(`location`)");
					for (Map.Entry<String, Location> home : this.homeMap.entrySet()) {
						statement.setString(1, this.uniqueId.toString());
						statement.setString(2, home.getKey());
						statement.setString(3, Utils.serializeLocation(home.getValue()));
						statement.addBatch();
						statement.clearParameters();
					}
					statement.executeBatch();
					statement.close();
				}
				if (money) {
					PreparedStatement statement = Main.getDatabaseManager().getConnection().prepareStatement("INSERT INTO `user_money` (`uniqueId`, `money`)" +
							"VALUES(?, ?) ON DUPLICATE KEY UPDATE `uniqueId` = VALUES(`uniqueId`), `money` = VALUES(`money`)");
					statement.setString(1, this.uniqueId.toString());
					statement.setLong(2, this.money);
					statement.executeUpdate();
					statement.close();
				}
				if (stats) {
					PreparedStatement statement = Main.getDatabaseManager().getConnection().prepareStatement("INSERT INTO `user_stats` (`uniqueId`, `kills`, `deaths`)" +
							"VALUES(?, ?, ?) ON DUPLICATE KEY UPDATE `uniqueId` = VALUES(`uniqueId`), `kills` = VALUES(`kills`), `deaths` = VALUES(`deaths`)");
					statement.setString(1, this.uniqueId.toString());
					statement.setInt(2, this.kills);
					statement.setInt(3, this.deaths);
					statement.executeUpdate();
					statement.close();
				}

				Main.getDatabaseManager().getConnection().commit();
				Main.getDatabaseManager().getConnection().setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}
}
