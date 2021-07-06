package io.github.divios.sortloot2inv;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public final class SortLoot2Inv extends JavaPlugin {

    private static SortLoot2Inv instance;
    private static boolean enable = true;

    @Override
    public void onEnable() {

        instance = this;
        listeners.getInstance();

        int pluginId = 10090;
        Metrics metrics = new Metrics(instance, pluginId);

        getCommand("SortLoot2Inv").setExecutor(new commands());
    }

    @Override
    public void onDisable() {

    }

    public static SortLoot2Inv getInstance() {
        return instance;
    }

    public static boolean isEnabledv() {
        return enable;
    }

    public static boolean toggleEnable() {
        return enable = !enable;
    }

}
