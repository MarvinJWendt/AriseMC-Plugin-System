package de.hardcorepvp.commands;

import de.hardcorepvp.model.Sysplayer;
import de.hardcorepvp.model.SysplayerList;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import de.hardcorepvp.model.Messages;

public class CommandHeal implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

	if (!(sender instanceof Player)) {
	    return false;
	}

	Player player = (Player) sender;
        Sysplayer sysplayer = SysplayerList.getSysplayer(player);

	if (args.length == 0) {

	    player.setHealth(20.0);
	    player.setFoodLevel(20);
	    player.setSaturation(20);
        sysplayer.removeNegativePotions();

	    player.sendMessage(Messages.formatMessage("Du wurdest geheilt"));
	    return true;
	}

	if (args.length == 1) {

	    if (!Bukkit.getPlayer(args[0]).isOnline()) {
		player.sendMessage(Messages.formatMessage(Messages.PLAYER_NOT_FOUND));
		return true;
	    }

	    Player target = Bukkit.getPlayer(args[0]);
        Sysplayer systarget = SysplayerList.getSysplayer(player);

	    target.setHealth(20.0);
	    target.setFoodLevel(20);
	    target.setSaturation(20);
        systarget.removeNegativePotions();

	    target.sendMessage(Messages.formatMessage("Du wurdest geheilt"));
	    player.sendMessage(Messages.formatMessage("Du hast " + target.getName() + " geheilt"));
	    return true;
	}

	if (args.length > 1) {
	    player.sendMessage(Messages.formatMessage(Messages.TOO_MANY_ARGUMENTS));
	    return true;
	}

	return false;
    }

}
