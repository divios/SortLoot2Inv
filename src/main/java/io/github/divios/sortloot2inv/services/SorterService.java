package io.github.divios.sortloot2inv.services;

import com.cryptomorin.xseries.ReflectionUtils;
import com.cryptomorin.xseries.messages.ActionBar;
import com.cryptomorin.xseries.particles.ParticleDisplay;
import com.github.divios.neptune_framework.core.annotations.Component;
import io.github.divios.sortloot2inv.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import redempt.redlib.nms.NMSHelper;
import redempt.redlib.nms.NMSObject;

import java.util.ArrayList;
import java.util.List;

@Component
public class SorterService {

    private static final String PERM_KEY = "SortLoot2Inv.use";

    public void onDeath(PlayerDeathEvent e) {
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

    public void onPickUp(EntityPickupItemEvent e) {
        if (!(e.getEntity() instanceof Player) ||
                !Utils.isDrop2InvItem(e.getItem().getItemStack())) return;

        Item item = e.getItem();
        ItemStack itemStack = item.getItemStack();
        Player p = (Player) e.getEntity();
        if (!p.hasPermission(PERM_KEY)) return;

        int slot = Utils.getSlot(itemStack);
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

        sendPackets(item, p);
        item.remove();

        spawnParticles(item, p);
        ActionBar.sendActionBar(p, ChatColor.DARK_AQUA + "Item set to previous slot");
    }

    private void spawnParticles(Item item, Player p) {
        ParticleDisplay.of(Particle.VILLAGER_HAPPY).spawn(item.getLocation().add(0, 1, 0), p);
        ParticleDisplay.of(Particle.VILLAGER_HAPPY).spawn(item.getLocation().add(0, 1, 0), p);
        ParticleDisplay.of(Particle.VILLAGER_HAPPY).spawn(item.getLocation().add(0, 1, 0), p);
    }

    private void sendPackets(Item item, Player p) {
        NMSObject packet = (ReflectionUtils.MINOR_NUMBER >= 17
                ? NMSHelper.getClass("net.minecraft.network.protocol.game.PacketPlayOutCollect")
                : NMSHelper.getClass(ReflectionUtils.getNMSClass("PacketPlayOutCollect").getName()))
                .getInstance(item.getEntityId(), p.getEntityId(), item.getItemStack().getAmount());

        ReflectionUtils.sendPacketSync(p, packet.getObject());
    }

}
