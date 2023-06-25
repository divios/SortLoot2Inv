package io.github.divios.sortloot2inv;

import com.github.divios.neptune_framework.guice.NeptuneApplication;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public final class SortLoot2Inv extends JavaPlugin {

    @Override
    public void onEnable() {
        NeptuneApplication.run(this);
    }

    @Override
    public void onDisable() {
    }

}
