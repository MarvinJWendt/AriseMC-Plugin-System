package de.hardcorepvp.commands;

import de.hardcorepvp.utils.Messages;
import de.hardcorepvp.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandCMDItem implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			return false;
		}
		Player player = (Player) sender;
		if (player.getItemInHand() == null) {
			player.sendMessage(Messages.formatMessage("Du musst ein Item in der Hand halten"));
			return true;
		}


		if (args.length == 0) {
			return true;
		}

		ItemStack item = player.getItemInHand();

		StringBuilder builder = new StringBuilder();
		for (int i = 0; args.length > i; i++) {
			if (i != 0) {
				builder.append(args[i] + " ");
			}

		}
		if (args.length == 1 && args[0].equalsIgnoreCase("#1")) {
			Utils.setCommandItem(item, "§k" + Messages.RANKUPCMD, Messages.RANKUPBOOK);
		}

		String commandString = builder.toString();
		Utils.setCommandItem(item, "§k" + builder.toString(), args[0]);
		player.sendMessage(Messages.formatMessage("Das Command Item mit dem Befehl " + commandString + " wurde created!"));
		return true;

	}

}
