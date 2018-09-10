package de.hardcorepvp.manager;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Manager {

	private boolean pvp;
	private int globalmute;
	private List<Player> vanishedPlayers;

	public Manager() {
		this.pvp = true;
		this.globalmute = 0;
		this.vanishedPlayers = new ArrayList<>();
	}

	public boolean isPvP() {
		return pvp;
	}

	public int getGlobalmute() {
		return globalmute;
	}

	public List<Player> getVanishedPlayers() {
		return vanishedPlayers;
	}

	public void setPvP(boolean pvp) {
		this.pvp = pvp;
	}

	public void setGlobalmute(int globalmute) {
		this.globalmute = globalmute;
	}

	public boolean isVanished(Player player) {
		return this.vanishedPlayers.contains(player);
	}

	public void addVanishedPlayer(Player player) {
		this.vanishedPlayers.add(player);
	}

	public void removeVanishedPlayer(Player player) {
		this.vanishedPlayers.remove(player);
	}

	public boolean canBypassGlobalmute(Player player) {
		//TODO DO STUFF BASED ON PERMISSION -> 0 = ALL, 1 = team, 2 = admins
		if (this.globalmute == 2) {
			return false;
		}
		return this.globalmute != 1;
	}

	public void removePlayer(Player player) {
		this.vanishedPlayers.remove(player);
	}
}
