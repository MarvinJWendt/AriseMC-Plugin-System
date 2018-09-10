package de.hardcorepvp.listener;

import de.hardcorepvp.Main;
import de.hardcorepvp.clan.Clan;
import de.hardcorepvp.clan.ClanMember;
import de.hardcorepvp.clan.ClanRank;
import de.hardcorepvp.data.User;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		User user = Main.getUserManager().getUser(player.getUniqueId());
		String message = event.getMessage();
		//TODO Prefix, Clan, Suffix
		//%1$s -> Spieler
		//%2$s -> Nachricht
		if (Main.getClanManager().hasClan(player.getUniqueId())) {
			Clan clan = Main.getClanManager().getClan(player.getUniqueId());
			ClanMember member = Main.getClanManager().getMember(player.getUniqueId());
			if (message.startsWith("#")) {
				event.setCancelled(true);
				message = message.substring(1).trim();
				if (member.getRank() == ClanRank.OWNER) {
					clan.broadcast("§6[Clan-Chat] §c" + player.getName() + "§7: §o" + message);
					return;
				}
				if (member.getRank() == ClanRank.TRUSTED) {
					clan.broadcast("§6[Clan-Chat] §3" + player.getName() + "§7: §o" + message);
					return;
				}
				clan.broadcast("§6[Clan-Chat] §7" + player.getName() + "§7: §o" + message);
				return;
			}
			event.setFormat(ChatColor.translateAlternateColorCodes('&', user.getGroup().getPrefix()) + " " + clan.getTagColor() + clan.getTag() + "§7*" + "%1$s§7: §e%2$s");
			return;
		}
		event.setFormat(ChatColor.translateAlternateColorCodes('&', user.getGroup().getPrefix()) + " %1$s§7: §e%2$s");
	}
}
