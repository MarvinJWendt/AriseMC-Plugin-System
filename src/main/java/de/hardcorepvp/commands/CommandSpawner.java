package de.hardcorepvp.commands;

import de.hardcorepvp.utils.Messages;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Player;

public class CommandSpawner implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			return false;
		}

		Player player = (Player) sender;

		if (args.length == 1) {

			Block block = player.getTargetBlock(null, 5);
			if (block.getType() != Material.MOB_SPAWNER) {
				player.sendMessage(Messages.formatMessage("Du musst einen Spawner anschauen!"));
				return true;
			}
			CreatureSpawner cs = (CreatureSpawner) block.getState();
			if (CreatureType.fromName(args[0]) != null) {

				player.sendMessage(Messages.formatMessage("Bitte gib einen Spawner Typen an:"));
				player.sendMessage(Messages.formatMessage(Messages.SPAWNERTYPES));
				return true;
			}
			cs.setCreatureType(CreatureType.fromName(args[0]));
			player.sendMessage(Messages.formatMessage("Der Spawner wurde geändert"));
			return true;
		}
		player.sendMessage(Messages.formatMessage(Messages.TOO_MANY_ARGUMENTS));
		return true;
	}

}
