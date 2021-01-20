package io.github.divios.dropitems2inv;

import org.bukkit.Bukkit;
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


    private static void init() {
        Listeners = new listeners();
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

        ItemStack item = e.getItem().getItemStack();
        Player p = (Player) e.getEntity();
        if(!p.hasPermission("DropItems2Inv.use")) return;
        int slot;

        slot = utils.getSlot(item);
        Player owner = utils.getOwner(item);

        if(slot == -1 || !p.equals(owner) || !utils.isEmpty(p, slot)) {
            utils.removeMetadata(item);
            return;
        }
        p.getInventory().setItem(slot, utils.removeMetadata(item.clone()));

        /*
        e.setCancelled(true);
        e.getItem().remove();
        */
        utils.destroyItem(p, utils.firstEmpty(p.getInventory()));

    }


}
