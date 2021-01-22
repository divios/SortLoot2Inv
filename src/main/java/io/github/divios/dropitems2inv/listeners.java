package io.github.divios.dropitems2inv;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import io.github.divios.dropitems2inv.packets.WrapperPlayServerCollect;
import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class listeners implements Listener {

    private static listeners Listeners = null;
    private static ProtocolManager protocolManager;
    private static DropItems2Inv main;

    private static void init() {
        Listeners = new listeners();
        main = DropItems2Inv.getInstance();
        if(main.getServer().getPluginManager().getPlugin("ProtocolLib") != null) {
            protocolManager = ProtocolLibrary.getProtocolManager();
        }
        Bukkit.getPluginManager().registerEvents(getInstance(), DropItems2Inv.getInstance());
    }

    public static listeners getInstance() {
        if(Listeners == null) init();
        return Listeners;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {

        if(!DropItems2Inv.isEnabledv()) return;

        Player p = e.getEntity();

        if (e.getKeepInventory() || !p.hasPermission("DropItems2Inv.use")) return;

        e.getDrops().clear();
        List<ItemStack> newDrops = new ArrayList<>();

        for (int i = 0; i < 41; i++) {
            ItemStack item = p.getInventory().getItem(i);
            if (utils.isEmpty(item)) continue;
            newDrops.add(utils.setDropMetadata(item, p, i));
        }

        e.getDrops().addAll(newDrops);
    }

    @EventHandler
    public void onEntityItemPickUp(EntityPickupItemEvent e) {

        if(!DropItems2Inv.isEnabledv()) return;

        if( !(e.getEntity() instanceof Player) ||
            !utils.isDrop2InvItem(e.getItem().getItemStack())) return;

        Item item = e.getItem();
        ItemStack itemStack = item.getItemStack();
        Player p = (Player) e.getEntity();
        if(!p.hasPermission("DropItems2Inv.use")) return;

        int slot;
        slot = utils.getSlot(itemStack);
        Player owner = utils.getOwner(itemStack);

        if(slot == -1 || !p.equals(owner) ||
                utils.inventoryFull(p.getInventory())) {
            item.setItemStack(utils.removeMetadata(itemStack));
            return;
        }

        e.setCancelled(true);

        if(!utils.isEmpty(p.getInventory().getItem(slot))) {
            item.setPickupDelay(12);
            item.setItemStack(utils.removeMetadata(itemStack));
            return;
        }

        p.getInventory().setItem(slot, utils.removeMetadata(itemStack.clone()));

        if(protocolManager != null) {
            WrapperPlayServerCollect wpsc = new WrapperPlayServerCollect();
            wpsc.setCollectedEntityId(e.getItem().getEntityId());
            wpsc.setCollectorEntityId(p.getEntityId());
            wpsc.sendPacket(p);

        }
        item.remove();

        /*try{
            p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 0.5F, 1.5F);
        } catch (NoSuchFieldError ignored) {}
        utils.destroyItem(p, utils.firstEmpty(p.getInventory())); */

    }


}
