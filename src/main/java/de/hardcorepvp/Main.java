package de.hardcorepvp;

import de.hardcorepvp.commands.*;
import de.hardcorepvp.database.DatabaseManager;
import de.hardcorepvp.file.ConfigFile;
import de.hardcorepvp.file.PermissionsFile;
import de.hardcorepvp.listener.*;
import de.hardcorepvp.manager.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	private static Main instance;
	private static ConfigFile configFile;
	private static PermissionsFile permissionsFile;
	private static DatabaseManager databaseManager;
	private static UserManager userManager;
	private static RankingManager rankingManager;
	private static PunishmentManager punishmentManager;
	private static PermissionManager permissionManager;
	private static ClanManager clanManager;

	@Override
	public void onEnable() {
		instance = this;
		configFile = new ConfigFile();
		permissionsFile = new PermissionsFile();
		databaseManager = new DatabaseManager(configFile);
		if (permissionsFile.getGroups().size() == 0 || permissionsFile.getDefaultGroupSize() == 0 || permissionsFile.getDefaultGroupSize() > 1) {
			Bukkit.getConsoleSender().sendMessage("§cDie 'permissions.yml' wurde nicht richtig konfiguriert!");
			Bukkit.getScheduler().runTaskLater(this, () -> Bukkit.getServer().setWhitelist(true), 60L);
			return;
		}
		if (!databaseManager.connect()) {
			Bukkit.getConsoleSender().sendMessage("§cEs konnte keine Verbindung zur Datenbank hergestellt werden...");
			Bukkit.getScheduler().runTaskLater(this, () -> Bukkit.getServer().setWhitelist(true), 60L);
			return;
		}
		userManager = new UserManager();
		rankingManager = new RankingManager();
		punishmentManager = new PunishmentManager();
		permissionManager = new PermissionManager();
		clanManager = new ClanManager();
		this.registerCommands();
		this.registerListeners();
	}

	@Override
	public void onDisable() {
		if (databaseManager.isConnected()) {
			databaseManager.disconnect();
		}
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
		getCommand("stack").setExecutor(new CommandStack());
		getCommand("help").setExecutor(new CommandHelp());
		getCommand("pvp").setExecutor(new CommandPvP());
		getCommand("invsee").setExecutor(new CommandInvsee());
		getCommand("goldswitch").setExecutor(new CommandGoldswitch());
		getCommand("ranking").setExecutor(new CommandRanking());
		getCommand("sell").setExecutor(new CommandSell());
		getCommand("giveall").setExecutor(new CommandGiveall());
		getCommand("ban").setExecutor(new CommandBan());
		getCommand("spawner").setExecutor(new CommandSpawner());
		getCommand("tpa").setExecutor(new CommandTpa());
		getCommand("tpahere").setExecutor(new CommandTpahere());
		getCommand("tpaccept").setExecutor(new CommandTpaccept());
		getCommand("rank").setExecutor(new CommandRank());
		getCommand("stats").setExecutor(new CommandStats());
		getCommand("clan").setExecutor(new CommandClan());
	}

	private void registerListeners() {
		this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
		this.getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerChatListener(), this);
		this.getServer().getPluginManager().registerEvents(new EntityDamageListener(), this);
		this.getServer().getPluginManager().registerEvents(new VoteListener(), this);
		this.getServer().getPluginManager().registerEvents(new AsyncPlayerPreLoginListener(), this);
	}

	public static Main getInstance() {
		return instance;
	}

	public static ConfigFile getConfigFile() {
		return configFile;
	}

	public static PermissionsFile getPermissionsFile() {
		return permissionsFile;
	}

	public static DatabaseManager getDatabaseManager() {
		return databaseManager;
	}

	public static UserManager getUserManager() {
		return userManager;
	}

	public static RankingManager getRankingManager() {
		return rankingManager;
	}

	public static PunishmentManager getPunishmentManager() {
		return punishmentManager;
	}

	public static PermissionManager getPermissionManager() {
		return permissionManager;
	}

	public static ClanManager getClanManager() {
		return clanManager;
	}
}
