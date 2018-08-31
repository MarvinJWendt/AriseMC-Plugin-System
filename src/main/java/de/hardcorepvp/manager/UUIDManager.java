package de.hardcorepvp.manager;

import de.hardcorepvp.Main;
import net.minecraft.util.com.google.common.cache.CacheBuilder;
import net.minecraft.util.com.google.common.cache.CacheLoader;
import net.minecraft.util.com.google.common.cache.LoadingCache;
import org.bukkit.Bukkit;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class UUIDManager {

	private LoadingCache<String, UUID> uuidCache;

	public UUIDManager() {
		this.uuidCache = CacheBuilder
				.newBuilder()
				.expireAfterAccess(10, TimeUnit.MINUTES)
				.build(new CacheLoader<String, UUID>() {
					@Override
					public UUID load(String name) throws Exception {
						return null;
					}
				});
	}

	public void getUniqueId(String name, Consumer<UUID> consumer) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
			try {
				UUID uniqueId = this.uuidCache.get(name);
				consumer.accept(uniqueId);
			} catch (ExecutionException e) {
				e.printStackTrace();
				consumer.accept(null);
			}
		});
	}

	public UUID getUniqueIdSync(String name) {
		try {
			return this.uuidCache.get(name);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}
}
