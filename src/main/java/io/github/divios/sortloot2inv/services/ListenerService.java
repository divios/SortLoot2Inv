package io.github.divios.sortloot2inv.services;

import com.cryptomorin.xseries.ReflectionUtils;
import com.cryptomorin.xseries.messages.ActionBar;
import com.cryptomorin.xseries.particles.ParticleDisplay;
import com.github.divios.neptune_framework.core.annotations.Component;
import com.github.divios.neptune_framework.core.annotations.EventListener;
import com.google.inject.Inject;
import io.github.divios.sortloot2inv.SortLoot2Inv;
import io.github.divios.sortloot2inv.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import redempt.redlib.nms.NMSHelper;
import redempt.redlib.nms.NMSObject;

import java.util.ArrayList;
import java.util.List;

@Component
public class ListenerService{

    @Inject
    private SorterService sorterService;

    @Inject
    private SettingsService settingsService;

    @EventListener(PlayerDeathEvent.class)
    public void onPlayerDeath(PlayerDeathEvent e) {
        if (!settingsService.getStatus()) return;

        sorterService.onDeath(e);
    }

    @EventListener(EntityPickupItemEvent.class)
    public void onEntityItemPickUp(EntityPickupItemEvent e) {
        if (!settingsService.getStatus()) return;

        sorterService.onPickUp(e);
    }

}
