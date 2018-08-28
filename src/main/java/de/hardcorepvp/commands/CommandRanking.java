package de.hardcorepvp.commands;

import de.hardcorepvp.Main;
import de.hardcorepvp.model.Ranking;
import de.hardcorepvp.utils.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.atomic.AtomicInteger;

public class CommandRanking implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return true;
		}
		Player player = (Player) sender;
		if (args.length == 0) {
			Main.getRankingManager().getRanking(Ranking.KILLS, (map) -> {
				AtomicInteger current = new AtomicInteger(1);
				player.sendMessage(Messages.RANKING_HEADER);
				player.sendMessage(Messages.RANKING_STRUCTURE);
				map.forEach((name, kills) -> {
					player.sendMessage(current.get() + ". §a" + name + " §7| " + kills);
					current.set(current.get() + 1);
				});
				player.sendMessage(Messages.RANKING_HEADER);
			});
		}
		if (args.length == 1) {
			Ranking ranking = Ranking.valueOf(args[0].toUpperCase());
			if (ranking == null) {
				player.sendMessage("/ranking kills, deaths, money");
				return true;
			}
			Main.getRankingManager().getRanking(ranking, (map) -> {
				AtomicInteger current = new AtomicInteger(1);
				player.sendMessage(Messages.RANKING_HEADER);
				player.sendMessage(Messages.RANKING_STRUCTURE.replace("%ranking%", ranking.getName()));
				map.forEach((name, value) -> {
					player.sendMessage(current.get() + ". §a" + name + " §7| " + value);
					current.set(current.get() + 1);
				});
				player.sendMessage(Messages.RANKING_HEADER);
			});
		}
		return true;
	}
}
