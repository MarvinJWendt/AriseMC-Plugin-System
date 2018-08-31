package de.hardcorepvp.commands;

import de.hardcorepvp.utils.Messages;
import de.hardcorepvp.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandTpa implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			return false;
		}

		Player player = (Player) sender;

		if (args.length == 0) {
			player.sendMessage(Messages.formatMessage(Messages.TOO_LESS_ARGUMENTS));
			return true;
		}
		if (args.length == 1) {
			if (Bukkit.getPlayer(args[0]) == null) {
				player.sendMessage(Messages.formatMessage(Messages.PLAYER_NOT_FOUND));
				return true;
			}

			Player target = Bukkit.getPlayer(args[0]);
			if (target == player) {
				player.sendMessage(Messages.formatMessage("Du kannst dir selber keine TPA Anfrage senden!"));
				return true;
			}

			if (Utils.hasTPACooldown(player)) {
				player.sendMessage(Messages.formatMessage("Du hast noch " + Utils.getTPACooldown(player) + " Sekunden cooldown"));
				return true;
			}
			Utils.sendTpa(player, target);
			player.sendMessage(Messages.formatMessage("Du hast " + target.getName() + " eine TPA Anfrage gesendet"));
			return true;

		}
		player.sendMessage(Messages.formatMessage(Messages.TOO_MANY_ARGUMENTS));
		return true;
	}


}
