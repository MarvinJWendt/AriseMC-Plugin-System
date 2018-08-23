package de.hardcorepvp.model;

import java.util.ArrayList;
import java.util.List;

public abstract class DatabaseLoader {

    private List<Runnable> runnables;
    private boolean loaded;

    public DatabaseLoader() {
        this.runnables = new ArrayList<>();
        this.loaded = false;
    }

    public List<Runnable> getRunnables() {
        return runnables;
    }

    public void addRunnable(Runnable runnable) {
        if (loaded) {
            runnable.run();
            return;
        }
        this.runnables.add(runnable);
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
        if (loaded) {
            this.runnables.forEach(Runnable::run);
        }
    }

    public abstract void load();

    public abstract void update(boolean money);
}
