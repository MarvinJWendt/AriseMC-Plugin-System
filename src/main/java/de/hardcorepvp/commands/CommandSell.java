package de.hardcorepvp.commands;

import de.hardcorepvp.utils.Messages;
import de.hardcorepvp.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CommandSell implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			return false;
		}

		Player player = (Player) sender;

		if (args.length == 0) {

			Inventory inv = player.getInventory();
			ItemStack[] invContents = inv.getContents();
			Integer moneyGained = 0;

			for (ItemStack item : invContents) {
				if (Utils.forSale(item)) {
					moneyGained += Utils.sellItem(item, player);
				}
			}
			player.sendMessage(Messages.formatMessage("Du hast dein Inventar verkauft und " + moneyGained + " bekommen"));
			return true;
		}
		player.sendMessage(Messages.formatMessage(Messages.TOO_MANY_ARGUMENTS));
		return true;
	}

}
