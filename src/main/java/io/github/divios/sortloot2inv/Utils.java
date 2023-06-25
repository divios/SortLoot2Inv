package io.github.divios.sortloot2inv;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Utils {

    public static final String SLOT_KEY = "Drop2InvSlot";
    public static final String OWNER_KEY = "Drop2InvOwner";

    public static ItemStack setDropMetadata(ItemStack item, Player p, int slot) {
        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setString(OWNER_KEY, p.getUniqueId().toString());
        nbtItem.setInteger(SLOT_KEY, slot);

        return nbtItem.getItem();
    }

    public static Player getOwner(ItemStack item) {

        NBTItem nbtItem = new NBTItem(item);
        if (!nbtItem.hasKey(OWNER_KEY)) return null;
        String uuidStr = nbtItem.getString(OWNER_KEY);

        return Bukkit.getPlayer(UUID.fromString(uuidStr));
    }

    public static Integer getSlot(ItemStack item) {

        NBTItem nbtItem = new NBTItem(item);
        if (!nbtItem.hasKey(SLOT_KEY)) return -1;

        if (nbtItem.getInteger(SLOT_KEY) == null) return -1;

        return nbtItem.getInteger(SLOT_KEY);
    }

    public static ItemStack removeMetadata(ItemStack item) {
        NBTItem nbtItem = new NBTItem(item);

        nbtItem.removeKey(OWNER_KEY);
        nbtItem.removeKey(SLOT_KEY);

        return nbtItem.getItem();
    }

    public static boolean isDrop2InvItem(ItemStack item) {
        NBTItem nbtItem = new NBTItem(item);
        return nbtItem.hasKey(SLOT_KEY) && nbtItem.hasKey(OWNER_KEY);
    }

    public static boolean isEmpty(Player p, int slot) {
        ItemStack item = p.getInventory().getItem(slot);
        return item == null || item.getType() == Material.AIR;
    }

    public static boolean isEmpty(ItemStack item) {
        return item == null || item.getType() == Material.AIR;
    }

    public static int firstEmpty(Inventory inv) {
        for (int i = 0; i < 41; i++) {
            ItemStack item = inv.getItem(i);
            if (isEmpty(item)) return i;
        }
        return -1;
    }

    public static boolean inventoryFull(Inventory inv) {
        for (ItemStack item : inv.getContents()) {
            if (isEmpty(item)) return false;
        }
        return true;
    }

}
