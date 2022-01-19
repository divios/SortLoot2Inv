package io.github.divios.sortloot2inv;

import com.cryptomorin.xseries.ReflectionUtils;
import com.cryptomorin.xseries.messages.ActionBar;
import com.cryptomorin.xseries.particles.ParticleDisplay;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import redempt.redlib.nms.NMSHelper;
import redempt.redlib.nms.NMSObject;

import java.util.ArrayList;
import java.util.List;

public class listeners implements Listener {

    private static final String PERM_KEY = "SortLoot2Inv.use";
    private static listeners Listeners = null;

    private static void init() {
        Listeners = new listeners();
        register();
    }

    public static listeners getInstance() {
        if (Listeners == null) init();
        return Listeners;
    }

    public static void register() {
        Bukkit.getPluginManager().registerEvents(getInstance(), SortLoot2Inv.get());
    }

    public static void unregister() {
        PlayerDeathEvent.getHandlerList().unregister(getInstance());
        EntityPickupItemEvent.getHandlerList().unregister(getInstance());
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        if (!SortLoot2Inv.isEnabledv()) return;

        Player p = e.getEntity();
        if (e.getKeepInventory() || !p.hasPermission(PERM_KEY)) return;

        e.getDrops().clear();
        List<ItemStack> newDrops = new ArrayList<>();

        for (int i = 0; i < 41; i++) {
            ItemStack item = p.getInventory().getItem(i);
            if (Utils.isEmpty(item)) continue;
            newDrops.add(Utils.setDropMetadata(item, p, i));
        }

        e.getDrops().addAll(newDrops);
    }


    @EventHandler
    public void onEntityItemPickUp(EntityPickupItemEvent e) {
        if (!SortLoot2Inv.isEnabledv()) return;

        if (!(e.getEntity() instanceof Player) ||
                !Utils.isDrop2InvItem(e.getItem().getItemStack())) return;

        Item item = e.getItem();
        ItemStack itemStack = item.getItemStack();
        Player p = (Player) e.getEntity();
        if (!p.hasPermission(PERM_KEY)) return;

        int slot;
        slot = Utils.getSlot(itemStack);
        Player owner = Utils.getOwner(itemStack);

        if (slot == -1 || !p.equals(owner) ||
                Utils.inventoryFull(p.getInventory())) {
            item.setItemStack(Utils.removeMetadata(itemStack));
            return;
        }

        e.setCancelled(true);

        if (!Utils.isEmpty(p.getInventory().getItem(slot))) {
            item.setPickupDelay(12);
            item.setItemStack(Utils.removeMetadata(itemStack));
            return;
        }

        p.getInventory().setItem(slot, Utils.removeMetadata(itemStack.clone()));

        NMSObject packet = (ReflectionUtils.VER >= 17
                ? NMSHelper.getClass("net.minecraft.network.protocol.game.PacketPlayOutCollect")
                : NMSHelper.getClass(ReflectionUtils.getNMSClass("PacketPlayOutCollect").getName()))
                .getInstance(item.getEntityId(), p.getEntityId(), item.getItemStack().getAmount());

        ReflectionUtils.sendPacketSync(p, packet.getObject());
        item.remove();

        ParticleDisplay.of(Particle.VILLAGER_HAPPY).spawn(item.getLocation().add(0, 1, 0), p);
        ParticleDisplay.of(Particle.VILLAGER_HAPPY).spawn(item.getLocation().add(0, 1, 0), p);
        ParticleDisplay.of(Particle.VILLAGER_HAPPY).spawn(item.getLocation().add(0, 1, 0), p);

        ActionBar.sendActionBar(p, ChatColor.DARK_AQUA + "Item set to previous slot");

        /*try{
            p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 0.5F, 1.5F);
        } catch (NoSuchFieldError ignored) {}
        utils.destroyItem(p, utils.firstEmpty(p.getInventory())); */

    }


}
