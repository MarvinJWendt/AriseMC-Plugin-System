package de.hardcorepvp.model;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

public class Sysplayer {

    private Player player;

    public Sysplayer(Player player) {

        this.player = player;

    }

    public Player returnPlayer() {

        return player;

    }

}
