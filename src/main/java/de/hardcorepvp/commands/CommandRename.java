package de.hardcorepvp.commands;

import de.hardcorepvp.model.Messages;
import de.hardcorepvp.model.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class CommandRename implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			return false;
		}

		Player player = (Player) sender;

		if (player.getItemInHand() != null && player.getItemInHand().getType() != Material.AIR) {

			if (args.length == 1) {

				if (!args[0].equalsIgnoreCase("reset")) {

					ArrayList<String> nameStrings = new ArrayList<String>();
					for (int i = 0; i < args.length; i++) {

						nameStrings.add(args[i]);

					}

					String name = String.join(" ", nameStrings);
					Utils.renameItemInHand(player, name);
					player.sendMessage(Messages.formatMessage("Dein Item wurde umbenannt"));
					player.sendMessage("Dein Item heisst nun: " + ChatColor.translateAlternateColorCodes('&', name));
					return true;

				}

				ItemStack item = player.getItemInHand();
				ItemMeta im = item.getItemMeta();
				im.setDisplayName(null);
				item.setItemMeta(im);
				return true;

			}
			if (args.length > 1) {

				player.sendMessage(Messages.formatMessage(Messages.TOO_MANY_ARGUMENTS));

			}
			if (args.length == 0) {

				player.sendMessage(Messages.formatMessage(Messages.TOO_LESS_ARGUMENTS));

			}

		}
		return false;
	}
}
