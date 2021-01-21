package io.github.divios.dropitems2inv;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class DropItems2Inv extends JavaPlugin {

    private static DropItems2Inv instance;
    private static boolean enable = true;
    private static ProtocolManager protocolManager = null;

    @Override
    public void onEnable() {

        instance = this;
        listeners.getInstance();

        getCommand("dropitems2inv").setExecutor(new commands());
    }

    @Override
    public void onDisable() {

    }

    public static DropItems2Inv getInstance() {
        return instance;
    }

    public static boolean isEnabledv() {
        return enable;
    }

    public static ProtocolManager getProtocolManager() {
        return protocolManager;
    }

    public static void toggleEnable() {
        enable = !enable;
    }

}
