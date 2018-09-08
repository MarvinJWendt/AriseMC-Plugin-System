package de.hardcorepvp.commands;

import de.hardcorepvp.utils.Messages;
import de.hardcorepvp.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandVanish implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}

		Player player = (Player) sender;

		if (args.length == 0) {
			if (!Utils.vanishedPlayers.contains(player)) {
				for (Player allPlayer : Bukkit.getOnlinePlayers()) {
					//TODO PERMISSION VANISH
					if (true) {
						allPlayer.hidePlayer(player);
					}
				}
				Utils.vanishedPlayers.add(player);
				player.sendMessage("Du bist nun im vanish");
				Bukkit.broadcastMessage(Messages.TEAMMEMBERLEFT);
				return true;
			}
			for (Player allPlayer : Bukkit.getOnlinePlayers()) {
				//TODO PERMISSION VANISH
				if (true) {
					allPlayer.showPlayer(player);
				}
			}
			Utils.vanishedPlayers.remove(player);
			player.sendMessage("Du bist nicht mehr im Vanish");
			Bukkit.broadcastMessage(Messages.TEAMMEMBERJOINED);
			return true;
		}
		player.sendMessage(Messages.TOO_MANY_ARGUMENTS);
		return true;
	}

}
