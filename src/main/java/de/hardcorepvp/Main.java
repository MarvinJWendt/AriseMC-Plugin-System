package de.hardcorepvp;

import de.hardcorepvp.commands.*;
import de.hardcorepvp.database.DatabaseManager;
import de.hardcorepvp.file.ConfigFile;
import de.hardcorepvp.listener.InventoryClickListener;
import de.hardcorepvp.listener.PlayerJoinListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	private static Main instance;
	private static ConfigFile configFile;
	private static DatabaseManager databaseManager;

	public static Main getInstance() {
		return instance;
	}

	public static ConfigFile getConfigFile() {
		return configFile;
	}

	public static DatabaseManager getDatabaseManager() {
		return databaseManager;
	}

	@Override
	public void onEnable() {
		instance = this;
		configFile = new ConfigFile();
		databaseManager = new DatabaseManager(configFile);
		if (!databaseManager.connect()) {
			Bukkit.getScheduler().runTaskLater(this, new Runnable() {

				@Override
				public void run() {
					Bukkit.getServer().shutdown();
				}
			}, 60L);
			return;
		}
		registerCommands();
		registerListeners();
	}

	@Override
	public void onDisable() {
		databaseManager.disconnect();
	}

	private void registerCommands() {
		getCommand("heal").setExecutor(new CommandHeal());
		getCommand("feed").setExecutor(new CommandFeed());
		getCommand("craft").setExecutor(new CommandCraft());
		getCommand("fix").setExecutor(new CommandFix());
		getCommand("ifix").setExecutor(new CommandiFix());
		getCommand("enderchest").setExecutor(new CommandEnderchest());
		getCommand("hat").setExecutor(new CommandHat());
		getCommand("rename").setExecutor(new CommandRename());
	}

	private void registerListeners() {
		rL(new InventoryClickListener());
		rL(new PlayerJoinListener());
	}

	private void rL(Listener listener) {

		getServer().getPluginManager().registerEvents(listener, this);

	}
}
