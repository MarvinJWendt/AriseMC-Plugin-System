package de.hardcorepvp.data;

import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class User {

	private UUID uniqueId;
	private UserStats userStats;
	private UserHomes userHomes;
	private UserMoney userMoney;
	private Map<String, Location> homes;
	private long firstLogin;
	private long lastLogin;
	private long money;
	private int kills;
	private int deaths;
	private int rank;

	public User(UUID uniqueId) {
		this.uniqueId = uniqueId;
		this.homes = new HashMap<>();
		this.firstLogin = -1L;
		this.lastLogin = -1L;
		this.money = -1L;
		this.kills = -1;
		this.deaths = -1;
		this.rank = -1;
	}

	public UUID getUniqueId() {
		return uniqueId;
	}

	public Map<String, Location> getHomes() {
		return homes;
	}

	public void addHome(String name, Location location) {
		this.homes.put(name, location);
	}

	public void removeHome(String name) {
		this.homes.remove(name);
	}

	public long getFirstLogin() {
		return firstLogin;
	}

	public void setFirstLogin(long firstLogin) {
		this.firstLogin = firstLogin;
	}

	public long getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(long lastLogin) {
		this.lastLogin = lastLogin;
	}

	public long getMoney() {
		return money;
	}

	public void setMoney(long money) {
		this.money = money;
	}

	public void addMoney(long money) {
		this.money += money;
	}

	public void removeMoney(long money) {
		this.money -= money;
	}

	public int getKills() {
		return kills;
	}

	public void setKills(int kills) {
		this.kills = kills;
	}

	public void addKill() {
		this.kills++;
	}

	public int getDeaths() {
		return deaths;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}

	public void addDeath() {
		this.deaths++;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
}
