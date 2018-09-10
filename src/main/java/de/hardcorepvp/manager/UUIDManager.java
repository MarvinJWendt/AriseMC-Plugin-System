package de.hardcorepvp.manager;

import de.hardcorepvp.model.Callback;
import net.minecraft.util.com.google.gson.Gson;
import net.minecraft.util.com.google.gson.GsonBuilder;
import net.minecraft.util.com.mojang.util.UUIDTypeAdapter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class UUIDManager {

	public static final long FEBRUARY_2015 = 1422748800000L;

	private static Gson gson = new GsonBuilder().registerTypeAdapter(UUID.class, new UUIDTypeAdapter()).create();

	private static final String UUID_URL = "https://api.mojang.com/users/profiles/minecraft/%s?at=%d";
	private static final String NAME_URL = "https://api.mojang.com/user/profiles/%s/names";

	private static List<ProfileHolder> profileCache = new CopyOnWriteArrayList<>();

	private String name;
	private UUID id;

	public static void getProfileHolderAt(String name, long timestamp, Callback<ProfileHolder> callback) {
		name = name.toLowerCase();
		for (ProfileHolder profileHolder : profileCache) {
			if (profileHolder.getName().equals(name)) {
				callback.onResult(profileHolder);
				return;
			}
		}
		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(String.format(UUID_URL, name, timestamp / 1000)).openConnection();
			connection.setReadTimeout(5000);
			UUIDManager data = gson.fromJson(new BufferedReader(new InputStreamReader(connection.getInputStream())), UUIDManager.class);

			ProfileHolder profileHolder = new ProfileHolder(data.id, name);
			profileCache.add(profileHolder);

			callback.onResult(profileHolder);
		} catch (Exception e) {
			e.printStackTrace();
			callback.onFailure(e.getCause());
		}
	}

	public static void getProfileHolderAt(UUID uniqueId, Callback<ProfileHolder> callback) {
		for (ProfileHolder profileHolder : profileCache) {
			if (profileHolder.getUniqueId().equals(uniqueId)) {
				callback.onResult(profileHolder);
				return;
			}
		}
		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(String.format(NAME_URL, UUIDTypeAdapter.fromUUID(uniqueId))).openConnection();
			connection.setReadTimeout(5000);
			UUIDManager[] nameHistory = gson.fromJson(new BufferedReader(new InputStreamReader(connection.getInputStream())), UUIDManager[].class);
			UUIDManager currentNameData = nameHistory[nameHistory.length - 1];

			ProfileHolder profileHolder = new ProfileHolder(uniqueId, currentNameData.name.toLowerCase());
			profileCache.add(profileHolder);

			callback.onResult(profileHolder);
		} catch (Exception e) {
			e.printStackTrace();
			callback.onFailure(e.getCause());
		}
	}

	public static class ProfileHolder {

		private UUID uniqueId;
		private String name;

		public ProfileHolder(UUID uniqueId, String name) {
			this.uniqueId = uniqueId;
			this.name = name;
		}

		public UUID getUniqueId() {
			return uniqueId;
		}

		public String getName() {
			return name;
		}
	}
}
