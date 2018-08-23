package de.hardcorepvp.commands;

import de.hardcorepvp.model.Functions;
import de.hardcorepvp.model.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

;

public class CommandiFix implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender;

        Functions.fixItems(player.getInventory().getContents());
        player.sendMessage(Messages.formatMessage("Dein Inventar wurde repariert"));
        return true;
    }
}
