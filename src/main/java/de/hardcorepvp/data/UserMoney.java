package de.hardcorepvp.data;

import de.hardcorepvp.Main;
import de.hardcorepvp.model.DatabaseLoader;
import org.bukkit.Bukkit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserMoney extends DatabaseLoader {

	private UUID uniqueId;
	private long money;

	public UserMoney(UUID uniqueId) {
		this.uniqueId = uniqueId;
		this.money = -1L;
		this.readFromDatabase();
	}

	public UUID getUniqueId() {
		return uniqueId;
	}

	public long getMoney() {
		return money;
	}

	public void addMoney(long money) {
		this.money += money;
		this.writeToDatabase();
	}

	public void setMoney(long money) {
		this.money = money;
		this.writeToDatabase();
	}

	@Override
	public void writeToDatabase() {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
			try {
				PreparedStatement statement = Main.getDatabaseManager().getConnection().prepareStatement(
						"INSERT INTO `user_money` (`uniqueId`, `money`)" +
								"VALUES(?, ?) ON DUPLICATE KEY UPDATE `uniqueId` = VALUES(`uniqueId`), `money` = VALUES(`money`)");
				statement.setString(1, this.uniqueId.toString());
				statement.setLong(2, this.money);
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
				PreparedStatement statement = Main.getDatabaseManager().getConnection().prepareStatement("SELECT `money` FROM `user_money` WHERE `uniqueId` = ?");
				statement.setString(1, this.uniqueId.toString());

				ResultSet resultSet = statement.executeQuery();
				if (resultSet.next()) {
					this.money = resultSet.getInt("money");
				} else {
					this.money = 0L;
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
