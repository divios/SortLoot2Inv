package io.github.divios.sortloot2inv.packets;

import com.comphenix.protocol.ProtocolConfig;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.error.BasicErrorReporter;
import com.comphenix.protocol.error.ErrorReporter;
import org.apache.commons.lang.Validate;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

public class ProtocolLibrary {
    /**
     * The minimum version ProtocolLib has been tested with.
     */
    public static final String MINIMUM_MINECRAFT_VERSION = "1.8";

    /**
     * The maximum version ProtocolLib has been tested with.
     */
    public static final String MAXIMUM_MINECRAFT_VERSION = "1.16.5";

    /**
     * The date (with ISO 8601 or YYYY-MM-DD) when the most recent version (1.16.5) was released.
     */
    public static final String MINECRAFT_LAST_RELEASE_DATE = "2021-01-15";

    /**
     * Plugins that are currently incompatible with ProtocolLib.
     */
    public static final List<String> INCOMPATIBLE = Arrays.asList("TagAPI");

    private static Plugin plugin;
    private static ProtocolConfig config;
    private static ProtocolManager manager;
    private static ErrorReporter reporter = new BasicErrorReporter();

    private static boolean updatesDisabled;
    private static boolean initialized;

    protected static void init(Plugin plugin, ProtocolConfig config, ProtocolManager manager, ErrorReporter reporter) {
        Validate.isTrue(!initialized, "ProtocolLib has already been initialized.");
        ProtocolLibrary.plugin = plugin;
        ProtocolLibrary.config = config;
        ProtocolLibrary.manager = manager;
        ProtocolLibrary.reporter = reporter;
        initialized = true;
    }

    /**
     * Gets the ProtocolLib plugin instance.
     * @return The plugin instance
     */
    public static Plugin getPlugin() {
        return plugin;
    }

    /**
     * Gets ProtocolLib's configuration
     * @return The config
     */
    public static ProtocolConfig getConfig() {
        return config;
    }

    /**
     * Retrieves the packet protocol manager.
     * @return Packet protocol manager
     */
    public static ProtocolManager getProtocolManager() {
        return manager;
    }

    /**
     * Retrieve the current error reporter.
     * @return Current error reporter.
     */
    public static ErrorReporter getErrorReporter() {
        return reporter;
    }

    /**
     * Disables the ProtocolLib update checker.
     */
    public static void disableUpdates() {
        updatesDisabled = true;
    }

    /**
     * Whether or not updates are currently disabled.
     * @return True if it is, false if not
     */
    public static boolean updatesDisabled() {
        return updatesDisabled;
    }
}
