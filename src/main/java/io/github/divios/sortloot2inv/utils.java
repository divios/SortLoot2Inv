package io.github.divios.sortloot2inv;

import de.tr7zw.changeme.nbtapi.NBTItem;
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

public class utils{

    public static ItemStack setDropMetadata(ItemStack item, Player p, int slot) {
        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setString("Drop2InvOwner", p.getUniqueId().toString());
        nbtItem.setInteger("Drop2InvSlot", slot);
        return nbtItem.getItem();
    }

    public static Player getOwner(ItemStack item) {

        NBTItem nbtItem = new NBTItem(item);
        if(!nbtItem.hasKey("Drop2InvOwner")) return null;
        String uuidStr = nbtItem.getString("Drop2InvOwner");

        return Bukkit.getPlayer(UUID.fromString(uuidStr));
    }

    public static Integer getSlot(ItemStack item) {

        NBTItem nbtItem = new NBTItem(item);
        if(!nbtItem.hasKey("Drop2InvSlot")) return -1;

        if(nbtItem.getInteger("Drop2InvSlot") == null) return -1;

        return nbtItem.getInteger("Drop2InvSlot");
    }

    public static ItemStack removeMetadata(ItemStack item) {
        NBTItem nbtItem = new NBTItem(item);

        nbtItem.removeKey("Drop2InvOwner");
        nbtItem.removeKey("Drop2InvSlot");

        return nbtItem.getItem();
    }

    public static boolean isDrop2InvItem(ItemStack item) {
        NBTItem nbtItem = new NBTItem(item);

        return nbtItem.hasKey("Drop2InvSlot") && nbtItem.hasKey("Drop2InvOwner");
    }

    public static boolean isEmpty(Player p, int slot) {
        ItemStack item = p.getInventory().getItem(slot);
        return item == null || item.getType() == Material.AIR;
    }

    public static boolean isEmpty(ItemStack item) {
        return item == null || item.getType() == Material.AIR;
    }

    public static void destroyItem(Player p, int slot) {
        Bukkit.getScheduler().runTaskLater(SortLoot2Inv.getInstance(), () -> {
            p.getInventory().setItem(slot, null);
        }, 1L);
    }

    public static String getEnableStr() {
        boolean enable = SortLoot2Inv.isEnabledv();

        if(enable) return ChatColor.GREEN + "enable";
        else return ChatColor.RED + "disable";
    }

    public static int firstEmpty(Inventory inv) {
        for(int i = 0; i<41; i++) {
            ItemStack item = inv.getItem(i);
            if(isEmpty(item)) return i;
        }
        return -1;
    }

    public static boolean inventoryFull(Inventory inv) {
        for(ItemStack item: inv.getContents()) {
            if(isEmpty(item)) return false;
        }
        return true;
    }

    public static void setDisplayName(ItemStack item, String name) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        item.setItemMeta(meta);
    }

    public static void setLore(ItemStack item, List<String> lore){
        ItemMeta meta = item.getItemMeta();
        List<String> coloredLore = new ArrayList<>();
        for(String s: lore) {
            coloredLore.add(ChatColor.translateAlternateColorCodes('&', s));
        }
        meta.setLore(coloredLore);
        item.setItemMeta(meta);
    }


}
