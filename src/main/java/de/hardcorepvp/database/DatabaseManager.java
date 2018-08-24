package de.hardcorepvp.database;

import de.hardcorepvp.Main;
import de.hardcorepvp.file.ConfigFile;
import org.bukkit.Bukkit;

import java.sql.*;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Consumer;

public class DatabaseManager {

	private String url;
	private Properties properties;
	private String host;
	private int port;
	private String schema;
	private String user;
	private String password;
	private Connection connection;

	public DatabaseManager(ConfigFile configFile) {
		this.host = configFile.getHost();
		this.port = configFile.getPort();
		this.schema = configFile.getSchema();
		this.user = configFile.getUser();
		this.password = configFile.getPassword();
		this.url = String.format("jdbc:mariadb://%s/%s", this.host, this.schema);
		this.properties = new Properties();
		this.properties.setProperty("user", user);
		this.properties.setProperty("password", password);
		this.properties.setProperty("port", String.valueOf(port));
		this.properties.setProperty("useSSL", "false");
		this.properties.setProperty("verifyServerCertificate", "true");
		this.properties.setProperty("requireSSL", "false");
		this.properties.setProperty("autoReconnect", "true");
	}

	public Connection getConnection() {
		return this.connection;
	}

	private void setupTables() {
		try {
			this.connection.setAutoCommit(false);
			PreparedStatement statement = this.connection.prepareStatement("CREATE TABLE IF NOT EXISTS `user_money` (" + "  `id` INT NOT NULL AUTO_INCREMENT," + "  `uniqueId` CHAR(36) NOT NULL," + "  `money` BIGINT UNSIGNED NOT NULL," + "  PRIMARY KEY (`id`)," + "  UNIQUE INDEX `uniqueId_UNIQUE` (`uniqueId` ASC))");
			statement.executeUpdate();
			statement.close();

			PreparedStatement statement1 = this.connection.prepareStatement("CREATE TABLE IF NOT EXISTS `user_stats` (" + "  `id` INT NOT NULL AUTO_INCREMENT," + "  `uniqueId` CHAR(36) NOT NULL," + "  `kills` INT UNSIGNED NOT NULL," + "  `deaths` INT UNSIGNED NOT NULL," + "  PRIMARY KEY (`id`)," + "  UNIQUE INDEX `uniqueId_UNIQUE` (`uniqueId` ASC))");
			statement1.executeUpdate();
			statement1.close();

			PreparedStatement statement2 = this.connection.prepareStatement("CREATE TABLE IF NOT EXISTS `user_homes` (" + "  `uniqueId` CHAR(36) NOT NULL," + "  `name` VARCHAR(50) NOT NULL," + "  `location` VARCHAR(100) NOT NULL," + "  INDEX `uniqueId_UNIQUE` (`uniqueId` ASC))");
			statement2.executeUpdate();
			statement2.close();

			this.connection.commit();
			this.connection.setAutoCommit(true);
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
	}

	public boolean connect() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			this.connection = DriverManager.getConnection(this.url, properties);
			this.setupTables();
			return true;
		} catch (SQLException | ClassNotFoundException exception) {
			Bukkit.getConsoleSender().sendMessage("§cEs konnte keine Verbindung zur Datenbank hergestellt werden...");
			exception.printStackTrace();
			return false;
		}
	}

	public void disconnect() {
		try {
			if (isConnected()) {
				this.connection.close();
			}
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
	}

	public boolean isConnected() {
		try {
			return this.connection == null || !this.connection.isValid(10) || this.connection.isClosed();
		} catch (SQLException exception) {
			exception.printStackTrace();
			return false;
		}
	}

	public ResultSet query(PreparedStatement preparedStatement) {
		try {
			if (!this.isConnected()) {
				this.connect();
			}
			return preparedStatement.executeQuery();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		return null;
	}

	public void queryAsync(PreparedStatement preparedStatement, Consumer<Optional<ResultSet>> consumer) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
			consumer.accept(Optional.ofNullable(this.query(preparedStatement)));
		});
	}

	public ResultSet query(String statement) {
		try {
			return this.getConnection().prepareStatement(statement).executeQuery();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		return null;
	}

	public void queryAsync(String statement, Consumer<Optional<ResultSet>> consumer) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
			consumer.accept(Optional.ofNullable(this.query(statement)));
		});
	}

	public boolean update(PreparedStatement preparedStatement) {
		try {
			if (!this.isConnected()) {
				this.connect();
			}
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException exception) {
			exception.printStackTrace();
			return false;
		}
	}

	public void updateAsync(PreparedStatement preparedStatement, Consumer<Boolean> consumer) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
			consumer.accept(this.update(preparedStatement));
		});
	}

	public boolean update(String statement) {
		try {
			return this.update(this.connection.prepareStatement(statement));
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		return false;
	}

	public void updateAsync(String statement, Consumer<Boolean> consumer) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
			consumer.accept(this.update(statement));
		});
	}
}
