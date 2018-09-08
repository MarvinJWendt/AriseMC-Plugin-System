package de.hardcorepvp;

import de.hardcorepvp.commands.*;
import de.hardcorepvp.database.DatabaseManager;
import de.hardcorepvp.file.ConfigFile;
import de.hardcorepvp.listener.*;
import de.hardcorepvp.manager.PunishmentManager;
import de.hardcorepvp.manager.RankingManager;
import de.hardcorepvp.manager.UUIDManager;
import de.hardcorepvp.manager.UserManager;
import de.hardcorepvp.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	private static Main instance;
	private static ConfigFile configFile;
	private static DatabaseManager databaseManager;
	private static UserManager userManager;
	private static RankingManager rankingManager;
	private static UUIDManager uuidManager;
	private static PunishmentManager punishmentManager;

	@Override
	public void onEnable() {
		Utils.registerCustomEnchantments();
		//TODO SAVE WORLDS IN CONFIG AND LOAD THEM PROPERLY
		World worldTest = WorldCreator.name("test").createWorld();
		instance = this;
		configFile = new ConfigFile();
		registerCommands();
		registerListeners();
		databaseManager = new DatabaseManager(configFile);
		if (!databaseManager.connect()) {
			Bukkit.getConsoleSender().sendMessage("§cEs konnte keine Verbindung zur Datenbank hergestellt werden...");
			Bukkit.getScheduler().runTaskLater(this, () -> Bukkit.getServer().setWhitelist(true), 60L);
			return;
		}
		userManager = new UserManager();
		rankingManager = new RankingManager();
		uuidManager = new UUIDManager();
		punishmentManager = new PunishmentManager();
	}

	@Override
	public void onDisable() {
		databaseManager.disconnect();
	}

	private void registerCommands() {

		getCommand("heal").setExecutor(new CommandHeal());
		getCommand("vanish").setExecutor(new CommandVanish());
		getCommand("world").setExecutor(new CommandWorld());
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
		getCommand("more").setExecutor(new CommandMore());
		getCommand("skin").setExecutor(new CommandSkin());
		getCommand("cmditem").setExecutor(new CommandCMDItem());

		getCommand("skype").setExecutor(new CommandSimple());
		getCommand("teamspeak").setExecutor(new CommandSimple());
		getCommand("vote").setExecutor(new CommandSimple());
		getCommand("youtube").setExecutor(new CommandSimple());
		getCommand("discord").setExecutor(new CommandSimple());
		getCommand("bewerben").setExecutor(new CommandSimple());
		getCommand("spenden").setExecutor(new CommandSimple());

	}

	private void registerListeners() {
		this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
		this.getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerChatListener(), this);
		this.getServer().getPluginManager().registerEvents(new EntityDamageListener(), this);
		this.getServer().getPluginManager().registerEvents(new VoteListener(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
	}

	public static Main getInstance() {
		return instance;
	}

	public static ConfigFile getConfigFile() {
		return configFile;
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

	public static UUIDManager getUUIDManager() {
		return uuidManager;
	}

	public static PunishmentManager getPunishmentManager() {
		return punishmentManager;
	}
}
