package de.hardcorepvp.commands;

import de.hardcorepvp.model.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandCraft implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.openWorkbench(player.getLocation(), true);
            return true;
        }
        if (args.length > 0) {
            Messages.formatMessage(Messages.TOO_MANY_ARGUMENTS);
            return true;
        }
        return false;
    }

}
