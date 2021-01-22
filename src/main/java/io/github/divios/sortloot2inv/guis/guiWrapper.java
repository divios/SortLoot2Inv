package io.github.divios.sortloot2inv.guis;

import org.bukkit.entity.Player;

public class guiWrapper {

    public static void openSettingsGui(Player p) {
        p.openInventory(settingsGui.getInstance().getInventory());
    }

}
