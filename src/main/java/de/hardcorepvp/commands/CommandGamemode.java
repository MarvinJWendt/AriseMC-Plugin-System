package de.hardcorepvp.commands;

import de.hardcorepvp.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandGamemode implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			return false;
		}

		Player player = (Player) sender;

		if (args.length == 0) {
			if (player.getGameMode() == GameMode.CREATIVE) {
				player.setGameMode(GameMode.SURVIVAL);
				player.sendMessage("Du bist nun im Survival Mode");
				return true;
			}
			player.setGameMode(GameMode.CREATIVE);
			player.sendMessage("Du bist nun im Creative Mode");
			return true;
		}
		String gamemode = "%p% bist nun im Gamemode %gm%";
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("survival")) {

				player.setGameMode(GameMode.SURVIVAL);
				player.sendMessage(gamemode.replace("%gm%", args[0]).replace("%p%", "Du"));
				return true;
			}
			if (args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("creative")) {
				player.setGameMode(GameMode.CREATIVE);
				player.sendMessage(gamemode.replace("%gm%", args[0]).replace("%p%", "Du"));
				return true;
			}
			if (args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("a") || args[0].equalsIgnoreCase("adventure")) {
				player.setGameMode(GameMode.ADVENTURE);
				player.sendMessage(gamemode.replace("%gm%", args[0]).replace("%p%", "Du"));
				return true;
			}
			player.sendMessage(Messages.SYNTAX_ERROR);
			return true;
		}
		if (args.length == 2) {
			if (Bukkit.getPlayer(args[1]) != null) {
				Player target = Bukkit.getPlayer(args[1]);
				if (args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("survival")) {
					target.setGameMode(GameMode.SURVIVAL);
					target.sendMessage(gamemode.replace("%gm%", args[0]).replace("%p%", "Du"));
					player.sendMessage(gamemode.replace("%gm%", args[0]).replace("%p%", target.getName()));
					return true;
				}
				if (args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("creative")) {
					target.setGameMode(GameMode.CREATIVE);
					target.sendMessage(gamemode.replace("%gm%", args[0]).replace("%p%", "Du"));
					player.sendMessage(gamemode.replace("%gm%", args[0]).replace("%p%", target.getName()));
					return true;
				}
				if (args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("a") || args[0].equalsIgnoreCase("adventure")) {
					target.setGameMode(GameMode.ADVENTURE);
					target.sendMessage(gamemode.replace("%gm%", args[0]).replace("%p%", "Du"));
					player.sendMessage(gamemode.replace("%gm%", args[0]).replace("%p%", target.getName()));
					return true;
				}
				player.sendMessage(Messages.SYNTAX_ERROR);
				return true;
			}
			player.sendMessage(Messages.PLAYER_NOT_FOUND);
			return true;
		}
		player.sendMessage(Messages.formatMessage(Messages.TOO_MANY_ARGUMENTS));
		return true;
	}

}
