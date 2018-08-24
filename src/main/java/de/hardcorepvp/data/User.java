package de.hardcorepvp.data;

import java.util.UUID;

public class User {

	private UUID uniqueId;
	private long firstLogin;
	private long lastLogin;
	private long money;
	private int kills;
	private int deaths;
	private int rank;

	public User(UUID uniqueId) {
		this.uniqueId = uniqueId;
	}

	public UUID getUniqueId() {
		return uniqueId;
	}
}
