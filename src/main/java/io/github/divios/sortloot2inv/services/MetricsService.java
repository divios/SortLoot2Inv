package io.github.divios.sortloot2inv.services;

import com.github.divios.neptune_framework.core.annotations.Component;
import com.google.inject.Inject;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.PostConstruct;

@Component
public class MetricsService {

    private int PLUGIN_ID = 10090;

    @Inject
    private JavaPlugin javaPlugin;

    @PostConstruct
    private void init() {
        new Metrics(javaPlugin, PLUGIN_ID);
    }

}
