package de.hardcorepvp.commands;

import de.hardcorepvp.utils.Messages;
import net.minecraft.server.v1_7_R4.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandGiveall implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			return false;
		}

		Player player = (Player) sender;

		if (args.length == 0) {
			if (player.getItemInHand() == null) {
				player.sendMessage(Messages.formatMessage("Du musst ein Item in der Hand halten"));
				return true;
			}

			ItemStack item = player.getItemInHand();
			//TODO for every player
			if (true) {
				Player pseudoplayer = Bukkit.getPlayer("xX_KevinLPHDPVPKill4hHDXxD");
				pseudoplayer.getInventory().addItem(item);

			}
			Bukkit.broadcastMessage(Messages.formatMessage("Jedem Spieler wurde " + item.getAmount() + "x " + item.getType() + " gegeben"));
			return true;
		}

		player.sendMessage(Messages.formatMessage(Messages.TOO_MANY_ARGUMENTS));
		return true;
	}

}
