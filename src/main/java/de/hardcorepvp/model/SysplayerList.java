package de.hardcorepvp.model;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class SysplayerList {

	public static HashMap<Player, Sysplayer> sysplayerList = new HashMap<Player, Sysplayer>();

	public static void registerSysplayer(Player player) {

		Sysplayer sysplayer = new Sysplayer(player);
		sysplayerList.put(player, sysplayer);

	}

	public static void unregisterSysplayer(Player player) {

		sysplayerList.remove(player);

	}

	public static Sysplayer getSysplayer(Player player) {

		return sysplayerList.get(player);

	}


}
