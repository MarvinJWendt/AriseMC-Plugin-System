package de.hardcorepvp.commands;

import de.hardcorepvp.utils.Messages;
import de.hardcorepvp.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandTpaccept implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

		if (!(sender instanceof Player)) {
			return false;
		}
		Player player = (Player) sender;

		if (args.length == 0) {
			if (Utils.currentTpaRequest.containsKey(player.getName())) {
				Player toTeleport = Bukkit.getPlayer(Utils.currentTpaRequest.get(player.getName()));
				Utils.currentTpaRequest.remove(player.getName());
				if (toTeleport != null) {
					toTeleport.teleport(player);
					return true;
				}
				player.sendMessage(Messages.PLAYER_NOT_FOUND);
				return true;
			}
			if (Utils.currentTpahereRequest.containsKey(player.getName())) {
				Player toTeleport = Bukkit.getPlayer(Utils.currentTpahereRequest.get(player.getName()));
				Utils.currentTpahereRequest.remove(player.getName());
				if (toTeleport != null) {
					player.teleport(toTeleport);
					return true;
				}
				player.sendMessage(Messages.PLAYER_NOT_FOUND);
				return true;


			}
		}

		player.sendMessage(Messages.TOO_MANY_ARGUMENTS);
		return true;
	}
}
