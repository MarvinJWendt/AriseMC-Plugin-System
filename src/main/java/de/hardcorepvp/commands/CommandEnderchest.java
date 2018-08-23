package de.hardcorepvp.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import de.hardcorepvp.model.Messages;

public class CommandEnderchest implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

	if (!(sender instanceof Player)) {
	    return false;
	}

	Player player = (Player) sender;

	if (args.length == 0) {
	    player.openInventory(player.getEnderChest());
	    return true;
	}
	if (args.length == 1) {
	    if (!(Bukkit.getPlayer(args[0]).isOnline())) {
		player.sendMessage(Messages.formatMessage(Messages.PLAYER_NOT_FOUND));
		return true;
	    }
	    Player target = Bukkit.getPlayer(args[0]);
	    player.openInventory(target.getEnderChest());
	    player.sendMessage(Messages.formatMessage("Du �ffnest die Enderchest  von " + target.getName()));
	    return true;
	}
	return false;
    }
}
