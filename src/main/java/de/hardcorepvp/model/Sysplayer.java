package de.hardcorepvp.model;

import org.bukkit.entity.Player;

public class Sysplayer {

	private Player player;

	public Sysplayer(Player player) {

		this.player = player;

	}

	public Player returnPlayer() {

		return player;

	}

}
