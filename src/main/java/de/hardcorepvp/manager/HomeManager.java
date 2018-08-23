package de.hardcorepvp.manager;

import de.hardcorepvp.Main;
import de.hardcorepvp.data.UserHomes;
import net.minecraft.util.com.google.common.cache.LoadingCache;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class HomeManager {

    private Map<UUID, UserHomes> homes;

    public HomeManager() {
        this.homes = new ConcurrentHashMap<>();
    }

    public Map<UUID, UserHomes> getHomes() {
        return homes;
    }

    public void addUser(UUID uniqueId, UserHomes userHomes) {

    }
}
