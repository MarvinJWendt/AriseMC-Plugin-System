package de.hardcorepvp.model;

import java.util.ArrayList;
import java.util.List;

public abstract class DatabaseLoader {

	private List<Runnable> readyExecutors;
	private boolean loaded;

	public DatabaseLoader() {
		this.readyExecutors = new ArrayList<>();
		this.loaded = false;
	}

	public List<Runnable> getReadyExecutors() {
		return readyExecutors;
	}

	public void addReadyExecutor(Runnable runnable) {
		if (loaded) {
			runnable.run();
			return;
		}
		this.readyExecutors.add(runnable);
	}

	public boolean isLoaded() {
		return loaded;
	}

	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
		if (loaded) {
			this.readyExecutors.forEach(Runnable::run);
		}
	}

	public abstract void writeToDatabase();

	public abstract void readFromDatabase();
}
