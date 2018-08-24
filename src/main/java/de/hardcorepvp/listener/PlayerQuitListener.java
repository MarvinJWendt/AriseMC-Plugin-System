package de.hardcorepvp.listener;

import de.hardcorepvp.model.SysplayerList;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    public void onPlayerQuit(PlayerQuitEvent event) {

        Player player = event.getPlayer();
        SysplayerList.unregisterSysplayer(player);

    }


}
