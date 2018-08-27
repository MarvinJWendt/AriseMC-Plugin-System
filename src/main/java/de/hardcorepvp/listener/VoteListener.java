package de.hardcorepvp.listener;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import de.hardcorepvp.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class VoteListener implements Listener {

	@EventHandler
	public void onPlayerVote(VotifierEvent event) {

		Vote vote = event.getVote();
		String playerstring = vote.getUsername();

		if (Bukkit.getPlayer(playerstring) == null) {

			Player player = Bukkit.getPlayer(playerstring);
			player.giveExp(100);
			Bukkit.broadcastMessage(Messages.formatMessage(playerstring + " hat gevoted!"));

		} else {

			Bukkit.broadcastMessage(Messages.formatMessage(playerstring + " hat gevoted ist jedoch Offline!"));

		}

	}


}
