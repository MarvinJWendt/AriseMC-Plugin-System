package de.hardcorepvp.commands;

import de.hardcorepvp.model.Messages;
import de.hardcorepvp.model.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandFix implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}

		Player player = (Player) sender;

		Utils.fixItems(player, true);
		player.sendMessage(Messages.formatMessage("Deine Items wurden repariert"));
		return true;

	}

}
