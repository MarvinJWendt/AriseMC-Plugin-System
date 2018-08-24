package de.hardcorepvp.commands;

import de.hardcorepvp.model.Sysplayer;
import de.hardcorepvp.model.SysplayerList;
import de.hardcorepvp.model.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import de.hardcorepvp.model.Messages;;

public class CommandiFix implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	if (!(sender instanceof Player)) {
	    return false;
	}

	Player player = (Player) sender;

		Utils.fixItems(player, false);
	player.sendMessage(Messages.formatMessage("Dein Inventar wurde repariert"));
	return true;
    }
}
