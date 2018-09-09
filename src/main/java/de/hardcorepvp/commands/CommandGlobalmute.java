package de.hardcorepvp.commands;

import de.hardcorepvp.utils.Messages;
import de.hardcorepvp.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandGlobalmute implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			return false;
		}

		Player player = (Player) sender;

		if (args.length == 0) {
			player.sendMessage("Globalmute Status: " + Utils.globalmute);
			return true;
		}
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("off") || args[0].equalsIgnoreCase("0")) {
				Utils.globalmute = 0;
				Bukkit.broadcastMessage("Globalmute wurde deaktivert");
				return true;
			}
			if (args[0].equalsIgnoreCase("on") || args[0].equalsIgnoreCase("1")) {
				Utils.globalmute = 1;
				Bukkit.broadcastMessage("Globalmute Stufe 1 wurde aktiviert");
				return true;
			}
			if (args[0].equalsIgnoreCase("2")) {
				Utils.globalmute = 2;
				Bukkit.broadcastMessage("Globalmute Stufe 2 wurde aktivert");
				return true;
			}
			player.sendMessage(Messages.SYNTAX_ERROR);
			return true;
		}
		if (args.length == 2) {
			if (args[0].equalsIgnoreCase("on")) {
				if (args[1].equalsIgnoreCase("1")) {
					Utils.globalmute = 1;
					Bukkit.broadcastMessage("Globalmute Stufe 1 wurde aktivert");
					return true;
				}
				if (args[1].equalsIgnoreCase("2")) {
					Utils.globalmute = 2;
					Bukkit.broadcastMessage("Globalmute Stufe 2 wurde aktivert");
					return true;
				}
			}
			player.sendMessage(Messages.SYNTAX_ERROR);
			return true;
		}
		player.sendMessage(Messages.formatMessage(Messages.TOO_MANY_ARGUMENTS));
		return true;
	}
}