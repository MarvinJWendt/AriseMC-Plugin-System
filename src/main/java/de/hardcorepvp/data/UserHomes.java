package de.hardcorepvp.data;

import org.bukkit.Location;

import java.util.Map;
import java.util.UUID;

public class UserHomes {

    private UUID uniqueId;
    private Map<String, Location> homes;

    public UserHomes(UUID uniqueId, Map<String, Location> homes) {
        this.uniqueId = uniqueId;
        this.homes = homes;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public Map<String, Location> getHomes() {
        return homes;
    }
}
