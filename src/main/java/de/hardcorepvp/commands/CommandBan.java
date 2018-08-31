package de.hardcorepvp.commands;

import de.hardcorepvp.Main;
import de.hardcorepvp.manager.PunishmentManager;
import de.hardcorepvp.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.function.Consumer;

public class CommandBan implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}
		Player player = (Player) sender;
		if (!player.hasPermission("system.ban")) {
			player.sendMessage(Messages.NO_PERMISSIONS);
			return true;
		}
		if (args.length == 0) {
			player.sendMessage("§cVerwendung: §b/ban <Spieler> <Grund>");
			return true;
		}
		if (args.length == 1) {
			Player target = Bukkit.getPlayer(args[0]);
			if (target != null) {
				if (player.equals(target)) {
					player.sendMessage("§cDu kannst dich nicht selber bannen!");
					return true;
				}
				if (target.hasPermission("system.ban.ignore")) {
					player.sendMessage("§cDu kannst diesen Spieler nicht bannen!");
					return true;
				}
				PunishmentManager.BanData banData = new PunishmentManager.BanData(target.getUniqueId(), "--", player.getName(), -1L, System.currentTimeMillis());
				Main.getPunishmentManager().setBanned(target.getUniqueId(), banData, new Consumer<Boolean>() {
					@Override
					public void accept(Boolean success) {
						if (!success) {
							player.sendMessage("§cDer Spieler konnte nicht gebannt werden!");
							return;
						}
						Bukkit.getScheduler().runTask(Main.getInstance(), () -> target.kickPlayer("Du wurdest gebannt!\nGrund: " + banData.getBanReason()));
						player.sendMessage("§aDer Spieler wurde erfolgreich gebannt!");
					}
				});
			}
			Main.getUUIDManager().getUniqueId(args[0], new Consumer<UUID>() {
				@Override
				public void accept(UUID uniqueId) {
					if (uniqueId == null) {
						player.sendMessage("§cDieser Spieler existiert nicht!");
						return;
					}
					PunishmentManager.BanData banData = new PunishmentManager.BanData(uniqueId, "--", player.getName(), -1L, System.currentTimeMillis());
					boolean success = Main.getPunishmentManager().setBannedSync(uniqueId, banData);
					if (!success) {
						player.sendMessage("§cDer Spieler konnte nicht gebannt werden!");
						return;
					}
					Bukkit.getScheduler().runTask(Main.getInstance(), () -> target.kickPlayer("Du wurdest gebannt!\nGrund: " + banData.getBanReason()));
					player.sendMessage("§aDer Spieler wurde erfolgreich gebannt!");
				}
			});
		}
		return true;
	}
}
