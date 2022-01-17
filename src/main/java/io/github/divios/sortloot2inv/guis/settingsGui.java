package io.github.divios.sortloot2inv.guis;

import io.github.divios.sortloot2inv.SortLoot2Inv;
import io.github.divios.sortloot2inv.listeners;
import io.github.divios.sortloot2inv.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

class settingsGui implements Listener, InventoryHolder {

    private static settingsGui SettingsGui = null;
    private static final SortLoot2Inv main = SortLoot2Inv.get();

    public static void init() {
        SettingsGui = new settingsGui();
        Bukkit.getPluginManager().registerEvents(SettingsGui, main);
    }

    protected static settingsGui getInstance() {
        if(SettingsGui == null) init();
        return SettingsGui;
    }

    @Override
    public Inventory getInventory() {
        Inventory inv = Bukkit.createInventory(getInstance(), 27, ChatColor.DARK_AQUA + "" +
                ChatColor.BOLD + "DropItems2Inv Settings");
        ItemStack toggleEnable = null;
        if(SortLoot2Inv.isEnabledv()) {
            toggleEnable = new ItemStack(Material.EMERALD_BLOCK);
            Utils.setDisplayName(toggleEnable, "&a&lEnable");
        }
        else {
            toggleEnable = new ItemStack(Material.REDSTONE_BLOCK);
            Utils.setDisplayName(toggleEnable, "&c&lDisable");
        }

        Utils.setLore(toggleEnable, Arrays.asList("&7Click to toggle the plugin's", "&7feature"));

        inv.setItem(13, toggleEnable);

        return inv;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {

        if(e.getView().getTopInventory().getHolder() != getInstance()) return;

        e.setCancelled(true);

        if(e.getSlot() != e.getRawSlot() ||
                Utils.isEmpty(e.getCurrentItem())) return;

        if(e.getSlot() == 13) {
            if (SortLoot2Inv.toggleEnable()) listeners.register();
            else listeners.unregister();

            e.getWhoClicked().sendMessage(ChatColor.DARK_AQUA + "DropItems2Inv > " +
                    ChatColor.GRAY + "Features are now " + Utils.getEnableStr());
            e.getWhoClicked().openInventory(getInventory());
        }

    }
}
