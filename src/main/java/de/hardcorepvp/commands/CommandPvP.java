package de.hardcorepvp.commands;

import de.hardcorepvp.utils.Messages;
import de.hardcorepvp.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandPvP implements CommandExecutor {


	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			return false;
		}

		Player player = (Player) sender;

		if (args.length == 0) {
			player.sendMessage(Messages.formatMessage("PvP ist " + Utils.pvp));
			return true;
		}
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("on")) {
				Utils.pvp = true;
				player.sendMessage(Messages.formatMessage("PvP ist nun an"));
				return true;
			}
			if (args[0].equalsIgnoreCase("off")) {
				Utils.pvp = false;
				player.sendMessage(Messages.formatMessage("PvP ist nun aus"));
				return true;
			}
			if (args[0].equalsIgnoreCase("toggle")) {
				Utils.pvp = !Utils.pvp;
				if (Utils.pvp) {
					player.sendMessage(Messages.formatMessage("PvP ist nun an"));
					return true;
				}
				player.sendMessage(Messages.formatMessage("PvP ist nun aus"));
				return true;
			}
			player.sendMessage(Messages.formatMessage(Messages.WRONG_ARGUMENTS));
			return true;
		}
		player.sendMessage(Messages.TOO_MANY_ARGUMENTS);
		return true;
	}
}
