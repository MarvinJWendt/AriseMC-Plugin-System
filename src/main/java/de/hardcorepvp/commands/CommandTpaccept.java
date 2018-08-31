package de.hardcorepvp.commands;

import de.hardcorepvp.utils.Messages;
import de.hardcorepvp.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandTpaccept implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			return false;
		}

		Player player = (Player) sender;

		if (args.length == 0) {

			if (Utils.tpaRequests.containsKey(player)) {
				Player toTeleport = Utils.tpaRequests.get(player);
				toTeleport.teleport(player.getLocation());
				Utils.tpaRequests.remove(player);
				return true;
			}
			player.sendMessage("KEINE TPA ANFRAGE");
			return true;
		}

		player.sendMessage(Messages.formatMessage(Messages.TOO_MANY_ARGUMENTS));
		return true;
	}

}
