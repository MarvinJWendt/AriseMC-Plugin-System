package de.hardcorepvp.commands;

import de.hardcorepvp.model.Messages;
import de.hardcorepvp.model.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandStack implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			return false;
		}

		Player player = (Player) sender;

		if (args.length >= 1) {

			if (!args[0].equalsIgnoreCase("64")) {

				ItemStack item = player.getItemInHand();
				item.setAmount(64);
				player.sendMessage(Messages.formatMessage("Du hast nun 64x"));
				return true;

			}

			Utils.stackItems(player);
			player.sendMessage(Messages.formatMessage("Deine Items wurden gestacked"));
			return true;

		}

		if (args.length == 0) {

			Utils.stackItems(player);
			player.sendMessage(Messages.formatMessage("Deine Items wurden gestacked"));
			return true;
		}
		return false;
	}

}
