package de.hardcorepvp.listener;

import de.hardcorepvp.model.SysplayerList;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        SysplayerList.registerSysplayer(player);

    }

}
